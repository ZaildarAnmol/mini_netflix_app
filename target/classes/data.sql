-- Initial seed data for genres and movies.
INSERT INTO genres(name) VALUES ('Action'),('Drama'),('Sci-Fi');

-- Insert some example movies. The posterUrl fields point to images in
-- src/main/resources/static/img. Feel free to add more movies following the
-- same pattern.
INSERT INTO movies(title, description, release_year, rating, poster_url, content_type)
VALUES
  ('Interstellar','A team of explorers travel through a wormhole',2014,8.6,'/img/interstellar.jpg','MOVIE'),
  ('The Dark Knight','Batman faces Joker',2008,9.0,'/img/darkknight.jpg','MOVIE');

-- Associate movies with genres. Movie id and genre id values correspond to the
-- order in which they were inserted above.
INSERT INTO movie_genres(movie_id, genre_id) VALUES (1,3),(2,1);