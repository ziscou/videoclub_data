package org.iesvdm.videoclub.repository;

import org.iesvdm.videoclub.domain.Categoria;

import java.util.List;
import java.util.Optional;

public interface CustomCategoriaRepository {
    List<Categoria> queryCustomCategoria(Optional<String> busOpt, Optional<String> odrOpt);
}
