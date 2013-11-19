package com.musiclibrary.euphonyapi.services;

import com.musiclibrary.euphonyapi.dto.PlaylistDTO;
import com.musiclibrary.euphonyapi.dto.SongDTO;
import java.util.List;
import org.springframework.dao.DataAccessException;

/**
 * Interface for Playlist service layer.
 * 
 * @author Tomas Smetanka #396209
 */
public interface PlaylistService {

    void create(PlaylistDTO playlistDTO) throws DataAccessException;
    
    void update(PlaylistDTO playlistDTO) throws DataAccessException;
    
    void delete(PlaylistDTO playlistDTO) throws DataAccessException;
    
    PlaylistDTO getById(Long id) throws DataAccessException;
    
    PlaylistDTO getByName(String name) throws DataAccessException;
    
    List<PlaylistDTO> getBySong(SongDTO songDTO) throws DataAccessException;
    
    List<PlaylistDTO> getAll();

//    void setPlaylistDAO(PlaylistDAO playlistDAO);
    
}