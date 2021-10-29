use CinestarBaza
go

create proc createDirector
	@Ime nvarchar(50),
    @Prezime nvarchar(50),
    @Tip int,
    @Id INT OUTPUT
as

    if(select count(*) from Osoba where Prezime=@Prezime and TipID=@Tip)<1
begin
    insert into Osoba values(@Ime,@Prezime,@Tip)
    set @id = SCOPE_IDENTITY()
end
go

create proc createMovie
	@Naziv nvarchar(300),
	@Opis nvarchar(300),
	@Trajanje int,
	@Slika nvarchar(90),
	@Id int OUTPUT
as 
begin 
	insert into Film values(@Naziv, @Opis, @Trajanje, @Slika)
	set @Id = SCOPE_IDENTITY()
end
go

create proc createMovieDjelatnik
	@FilmID int,
	@OsobaID int,
	@Id int OUTPUT
as
begin
	insert into FilmDjelatnik values (@FilmID,@OsobaID)
	set @id = SCOPE_IDENTITY()
end
go

create proc createMovieGenre
    @FilmId int,
    @ZanrId int,
    @Id  int output
as
begin
    insert into ZanrFilm values (@FilmID,@ZanrId)
    set @id = SCOPE_IDENTITY()
end
go

create proc createGenre
	@Naziv nvarchar(50),
	@Id int OUTPUT
as
	if(select count(*) from Zanr where Naziv=@Naziv)<1
begin
	insert into Zanr values (@Naziv)
	set @Id = SCOPE_IDENTITY()
end
go

create proc createGenreToSpecificMovie
	@idMovie int,
	@idGenre int
as
begin 
	insert into ZanrFilm(FilmId,ZanrID) values(@idMovie,@idGenre)
end
go

create proc createOneMovie
	@Naziv nvarchar(100),
	@Opis nvarchar(max),
	@Trajanje int,
	@PicturePath nvarchar(max)
as
begin
	insert into Film values (@Naziv,@Opis,@Trajanje,@PicturePath)
end
go

create proc deleteMovie
	@IDFilm int	 
as 
begin 
	delete  
		from 
			Film
		where 
		IDFilm = @IDFilm
end
go

create proc deleteMovieDjelatnik
as
	begin
		delete from FilmDjelatnik
	end
go

create proc deleteMovieGenre
as
	begin
		delete from ZanrFilm
	end
go

create proc deleteMovies
as 
begin 
	delete from Film
end
go

create proc deletePerson
	@IDOsoba INT	 
as 
begin 
	delete  
	from 
			Osoba
	where 
		IDOsoba = @IDOsoba
end
go

create proc MoviePerson
	@FilmID int,
	@OsobaID int,
	@Id int OUTPUT
as
begin
	insert into FilmDjelatnik values(@FilmID,@OsobaID)
	set @id = SCOPE_IDENTITY()
end
go

create proc selectActors
as 
begin 
	select * from Osoba where TipID=2
end
go

create proc selectDirectors
as 
begin 
	select * from Osoba where TipID=1
end
go

create proc selectDirectorsInMovies
	@FilmId int
as
begin
	select distinct Osoba.Ime, Osoba.Prezime from FilmDjelatnik
	inner join Osoba on FilmDjelatnik.OsobaID = IDOsoba
	where FilmId = @FilmId
end
go

create proc selectMovie
	@IDFilm int
as 
begin 
	select * from Film where IDFilm = @IDFilm
end
go

create proc selectMovies
as 
begin 
	select * from Film
end
go

create proc selectGenres
as 
begin 
	select * from Zanr
end
go

create proc selectMovieByName
	@Name nvarchar(100)
as
begin
	select * from Film where Film.Naziv=@Name
end
go

create proc updateMovie
	@Naziv nvarchar(300),
	@Opis nvarchar(300),
	@Trajanje int,
	@IDFilm int	 
as 
begin 
	UPDATE Film set 
		Naziv = @Naziv,
		Opis = @Opis,
		Trajanje = @Trajanje
	where 
		IDFilm = @IDFilm
end