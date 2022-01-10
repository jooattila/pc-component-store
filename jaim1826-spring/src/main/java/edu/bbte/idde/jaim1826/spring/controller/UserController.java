package edu.bbte.idde.jaim1826.spring.controller;

import edu.bbte.idde.jaim1826.spring.dao.UserDao;
import edu.bbte.idde.jaim1826.spring.exceptions.NotFoundException;
import edu.bbte.idde.jaim1826.spring.model.User;
import edu.bbte.idde.jaim1826.spring.model.dto.incoming.UserReqDto;
import edu.bbte.idde.jaim1826.spring.model.dto.mapper.UserMapper;
import edu.bbte.idde.jaim1826.spring.model.dto.outgoing.TokenResDto;
import edu.bbte.idde.jaim1826.spring.model.dto.outgoing.UserResDto;
import edu.bbte.idde.jaim1826.spring.service.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.security.SecureRandom;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Collection;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserDao userDao;

    @Autowired
    private UserMapper mapper;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private TokenManager tokenManager;

    @GetMapping
    public Collection<UserResDto> findAll() throws SQLException {
        return mapper.userListToDto(userDao.findAll());
    }

    @GetMapping("/{id}")
    public UserResDto findById(@PathVariable("id") Long id) throws SQLException {
        User user = userDao.getById(id);
        if (user == null) {
            throw new NotFoundException();
        }

        return mapper.userToUserDto(user);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TokenResDto create(@RequestBody @Valid UserReqDto userReqDto, HttpServletResponse response)
            throws SQLException {

        User user = mapper.userDtoToUser(userReqDto);
        String encodedPassword = encoder.encode(user.getPassword());

        user.setPassword(encodedPassword);
        user.setLastLogin(new Date(System.currentTimeMillis()));
        user.setIsAdmin(false);

        User savedUser = userDao.save(user);

        response.addHeader("Location", "/users/" + savedUser.getId());

        TokenResDto tokenResDto = new TokenResDto();
        tokenResDto.setToken(tokenManager.createToken(user));

        return tokenResDto;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable("id") Long id, @RequestBody @Valid UserReqDto userDto)
            throws SQLException {
        User user = mapper.userDtoToUser(userDto);
        user.setId(id);
        userDao.save(user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) throws SQLException {
        userDao.deleteById(id);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public TokenResDto login(@RequestBody @Valid UserReqDto userReqDto) throws SQLException {
        User user = userDao.getByUsername(userReqDto.getUsername());
        if (user != null && encoder.matches(userReqDto.getPassword(), user.getPassword())) {
            user.setLastLogin(new Date(System.currentTimeMillis()));
            userDao.save(user);
            TokenResDto tokenResDto = new TokenResDto();
            tokenResDto.setToken(tokenManager.createToken(user));
            return tokenResDto;
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }
}
