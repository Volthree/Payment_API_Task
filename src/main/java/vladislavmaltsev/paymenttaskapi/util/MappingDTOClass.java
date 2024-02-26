package vladislavmaltsev.paymenttaskapi.util;

import org.modelmapper.ModelMapper;

public class MappingDTOClass {
    private MappingDTOClass() {}
    public static <D, C> C mapDTOAndClass(D dto, Class<C> clazz) {
        ModelMapper modelMapper = new ModelMapper();
        return dto != null ? modelMapper.map(dto, clazz) : null;
    }
}
