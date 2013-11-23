package com.musiclibrary.euphonyweb;

import com.musiclibrary.euphonyapi.dto.AlbumDTO;
import com.musiclibrary.euphonyapi.dto.ArtistDTO;
import com.musiclibrary.euphonyapi.dto.GenreDTO;
import com.musiclibrary.euphonyapi.dto.SongDTO;
import com.musiclibrary.euphonyapi.services.AlbumService;
import com.musiclibrary.euphonyapi.services.ArtistService;
import com.musiclibrary.euphonyapi.services.GenreService;
import com.musiclibrary.euphonyapi.services.SongService;
import java.util.List;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.ValidationErrorHandler;
import net.sourceforge.stripes.validation.ValidationErrors;

/**
 * Explore action bean for showing songs, albums and artists in index.
 * 
 * @author Tomas Smetanka #396209
 */
@UrlBinding("/explore/{$event}")
public class ExploreActionBean extends BaseActionBean implements ValidationErrorHandler {

    @SpringBean
    protected SongService songService;
    protected GenreService genreService;
    @SpringBean
    protected AlbumService albumService;
    @SpringBean
    protected ArtistService artistService;
    private GenreDTO genre;
    private SongDTO song;
    private AlbumDTO album;
    private ArtistDTO artis;
    private List<GenreDTO> genres;
    private List<SongDTO> songs;
    private List<AlbumDTO> albums;
    private List<ArtistDTO> artists;

    @DefaultHandler
    public Resolution songs() {
        //log.debug("list()");
        songs = songService.getAll();
        return new ForwardResolution("/explore/songs.jsp");
    }

    public Resolution albums() {
        //log.debug("list()");
        albums = albumService.getAllAlbums();
        return new ForwardResolution("/explore/albums.jsp");
    }

    public Resolution artists() {
        //log.debug("list()");
        artists = artistService.getAll();
        return new ForwardResolution("/explore/artists.jsp");
    }

    @Override
    public Resolution handleValidationErrors(ValidationErrors errors) throws Exception {
        songs = songService.getAll();
        return null;
    }

    //--- getters and setters ----
    public List<GenreDTO> getGenres() {
        return genres;
    }

    public List<AlbumDTO> getAlbums() {
        return albums;
    }

    public List<ArtistDTO> getArtists() {
        return artists;
    }

    public List<SongDTO> getSongs() {
        return songs;
    }

    public GenreDTO getGenre() {
        return genre;
    }

    public void setGenre(GenreDTO genre) {
        this.genre = genre;
    }

    public SongDTO getSong() {
        return song;
    }

    public void setSong(SongDTO song) {
        this.song = song;
    }

    public AlbumDTO getAlbum() {
        return album;
    }

    public void setAlbum(AlbumDTO album) {
        this.album = album;
    }

    public ArtistDTO getArtis() {
        return artis;
    }

    public void setArtis(ArtistDTO artis) {
        this.artis = artis;
    }
}