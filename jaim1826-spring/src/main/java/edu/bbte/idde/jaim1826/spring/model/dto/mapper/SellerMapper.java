package edu.bbte.idde.jaim1826.spring.model.dto.mapper;

import edu.bbte.idde.jaim1826.spring.model.Seller;
import edu.bbte.idde.jaim1826.spring.model.dto.incoming.SellerReqDto;
import edu.bbte.idde.jaim1826.spring.model.dto.outgoing.SellerResDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.Collection;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class SellerMapper {

    public abstract Seller sellerDtoToSeller(SellerReqDto sellerReqDto);

    public abstract SellerResDto sellerToSellerDto(Seller seller);

    public abstract Collection<SellerResDto> sellerListToDto(Collection<Seller> sellers);
}
