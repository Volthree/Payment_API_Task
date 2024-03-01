package vladislavmaltsev.paymenttaskapi.util;

import org.modelmapper.ModelMapper;

import java.util.Optional;

public class MappingDTOClass {
    public static <D, C> Optional<C> mapDTOAndClass(D dto, Class<C> clazz) {
        ModelMapper modelMapper = new ModelMapper();
        return Optional.ofNullable(modelMapper.map(dto, clazz));
    }
}
