package com.musiclibrary.euphonybusinesslogicimplementation.dao.impl;

import com.musiclibrary.euphonybusinesslogicimplementation.dao.AlbumDAO;
import com.musiclibrary.euphonybusinesslogicimplementation.entities.Album;
import com.musiclibrary.euphonybusinesslogicimplementation.entities.Artist;
import com.musiclibrary.euphonybusinesslogicimplementation.entities.Genre;
import com.musiclibrary.euphonybusinesslogicimplementation.entities.Song;
import com.musiclibrary.euphonybusinesslogicimplementation.util.Util;
import java.util.Collections;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.joda.time.DateTime;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

/**
 *
 *
 * @author Branislav Novotny
 */
@Repository
public class AlbumDAOImpl implements AlbumDAO {

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager em;

    public AlbumDAOImpl() {
    }

    public AlbumDAOImpl(EntityManager em) {
        this.em = em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public void create(Album entity) {
        try {
            Util.validateAlbum(entity);

            if (entity.getId() != null) {
                throw new IllegalArgumentException("This album entity is already in databse.");
            }

            em.persist(entity);
            em.flush();
            em.detach(entity);
        } catch (PersistenceException | IllegalArgumentException ex) {
            throw new DataAccessException(ex.getMessage(), ex) {
            };
        }
    }

    @Override
    public void update(Album entity) {
        try {
            Util.validateAlbum(entity);

            if (entity.getId() == null) {
                throw new IllegalArgumentException("This album entity cannot have null id.");
            }
            if (em.find(Album.class, entity.getId()) == null) {
                throw new IllegalArgumentException("This album entity does not exist in database.");
            }

            em.merge(entity);
            em.flush();
            em.detach(entity);
        } catch (PersistenceException | IllegalArgumentException ex) {
            throw new DataAccessException(ex.getMessage(), ex) {
            };
        }
    }

    @Override
    public void delete(Album entity) {
        try {
            Util.validateAlbum(entity);

            if (entity.getId() == null) {
                throw new IllegalArgumentException("This album entity cannot have null id.");
            }
            if (em.find(Album.class, entity.getId()) == null) {
                throw new IllegalArgumentException("This album entity does not exist in database.");
            }

            Album objectTemp = em.merge(entity);

            em.remove(objectTemp);
        } catch (PersistenceException | IllegalArgumentException ex) {
            throw new DataAccessException(ex.getMessage(), ex) {
            };
        }
    }

    @Override
    public Album getById(Long id) {
        try {
            if (id == null) {
                throw new IllegalArgumentException("Id cannot be null.");
            }

            Album objectTemp = (Album) em.find(Album.class, id);
            return objectTemp;
        } catch (PersistenceException | IllegalArgumentException ex) {
            throw new DataAccessException(ex.getMessage(), ex) {
            };
        }
    }

    @Override
    public List<Album> getAll() {
        try {
            Query q = em.createQuery("from Album");
            List<Album> albums = q.getResultList();
            return Collections.unmodifiableList(albums);
        } catch (PersistenceException | IllegalArgumentException ex) {
            throw new DataAccessException(ex.getMessage(), ex) {
            };
        }
    }

    @Override
    public Album getByTitle(String title) {
        try {
            if (title == null) {
                throw new IllegalArgumentException("Title is null");
            }
            Query q = em.createQuery("from Album where title=:title");
            q.setParameter("title", title);
            try {
                Album album = (Album) q.getSingleResult();
                return album;
            } catch (NoResultException ex) {
                return null;
            }
        } catch (PersistenceException | IllegalArgumentException ex) {
            throw new DataAccessException(ex.getMessage(), ex) {
            };
        }
    }

    @Override
    public List<Album> getByGenre(Genre genre) {
        try {
            if (genre == null) {
                throw new IllegalArgumentException("Genre is null");
            }
            Query q = em.createQuery("SELECT x FROM Album x WHERE (:genre) IN elements(x.genres)");
            q.setParameter("genre", genre);
            try {
                List<Album> albums = q.getResultList();
                return albums;
            } catch (NoResultException ex) {
                return null;
            }
        } catch (PersistenceException | IllegalArgumentException ex) {
            throw new DataAccessException(ex.getMessage(), ex) {
            };
        }
    }

    @Override
    public List<Album> getByArtist(Artist artist) {
        try {
            if (artist == null) {
                throw new IllegalArgumentException("Artist is null");
            }
            Query q = em.createQuery("SELECT x FROM Album x WHERE (:artist) IN elements(x.artists)");
            q.setParameter("artist", artist);
            try {
                List<Album> albums = q.getResultList();
                return albums;
            } catch (NoResultException ex) {
                return null;
            }
        } catch (PersistenceException | IllegalArgumentException ex) {
            throw new DataAccessException(ex.getMessage(), ex) {
            };
        }
    }

    @Override
    public List<Album> getByReleaseYear(Integer year) { //ceknut si je to spravnetry
        try {
            if (year == null) {
                throw new IllegalArgumentException("Year is null");
            }
            Query q = em.createQuery("select x from Album x WHERE x.releaseDate BETWEEN :from AND :to");
            q.setParameter("from", new DateTime(year, 1, 1, 0, 0));
            q.setParameter("to", new DateTime(year, 12, 31, 23, 59));
            try {
                List<Album> albums = q.getResultList();
                return albums;
            } catch (NoResultException ex) {
                return null;
            }
        } catch (PersistenceException | IllegalArgumentException ex) {
            throw new DataAccessException(ex.getMessage(), ex) {
            };
        }
    }

    @Override
    public List<Song> getSongsByAlbum(Album album) {
        try {
            if (album == null) {
                throw new IllegalArgumentException("Album is null");
            }
            Query q = em.createQuery("select x from Song x WHERE x.album = :album");
            q.setParameter("album", album);
            try {
                List<Song> songs = q.getResultList();
                return songs;
            } catch (NoResultException ex) {
                return null;
            }
        } catch (PersistenceException | IllegalArgumentException ex) {
            throw new DataAccessException(ex.getMessage(), ex) {
            };
        }
    }
}
