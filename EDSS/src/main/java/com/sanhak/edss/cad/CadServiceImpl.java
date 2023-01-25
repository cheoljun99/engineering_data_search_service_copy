package com.sanhak.edss.cad;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CadServiceImpl implements CadService {
    private final CadRepository cadRepository;

    public Cad saveCadFile(Cad cad) {
        cadRepository.save(cad);
        return cad;
    }
}
