package com.musiclibrary.euphonyapi.services;

import com.musiclibrary.euphonyapi.dto.ArtistDTO;
import com.musiclibrary.euphonyapi.dto.SongDTO;
import java.util.List;

/**
 *
 * @author Medo
 */
public interface ArtistService {
    
    void create(ArtistDTO artistDTO);

    void update(ArtistDTO artistDTO);

    void delete(ArtistDTO artistDTO);

    ArtistDTO getById(Long id);

    List<ArtistDTO> getAll();

    ArtistDTO getByName(String name);

    List<SongDTO> getSongsByArtist(ArtistDTO artist);
    
    List<ArtistDTO> getArtistsByNameSub(String phrase);
            
}
