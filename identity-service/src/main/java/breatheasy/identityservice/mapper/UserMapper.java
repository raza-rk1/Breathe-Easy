package breatheasy.identityservice.mapper;

import breatheasy.identityservice.dto.RegisterDto;
import breatheasy.identityservice.model.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    User registerDtoToUser(RegisterDto registerDto);
}
