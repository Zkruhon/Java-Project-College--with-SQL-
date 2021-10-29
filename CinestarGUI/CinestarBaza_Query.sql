use CinestarBaza
go

create table KorisnikRazina
(
	IDKorisnikRazina int primary Key identity,
	Naziv nvarchar(50)
)
go

insert into KorisnikRazina (Naziv) values ('administrator'),('Korisnik')
go

create table Korisnik
(
	IDKorisnik int primary key identity,
	KorisnickoIme nvarchar(50),
	Lozinka nvarchar(500),
	KorisnikRazinaID int foreign key references KorisnikRazina(IDKorisnikRazina)
)
go

insert into Korisnik(KorisnickoIme,Lozinka,KorisnikRazinaID)values
('admin123',HASHBYTES('SHA1','Password'),1),
('user123',HASHBYTES('SHA1','123'),2)

go 

create table Zanr
(
	IDZanr int primary key identity,
	Naziv nvarchar(50),
)
go

create table Tip
(
	IDTip int primary key identity,
	Naziv nvarchar(50)
)
go

insert into Tip(Naziv) values ('Redatelj'),('Glumac')
go

create table Osoba
(
	IDOsoba int primary key identity,
	Ime nvarchar(50),
	Prezime nvarchar(50),
	TipID int foreign key references Tip(IDTip)
)
go

create table Film
(
	IDFilm int primary key identity,
	Naziv nvarchar(50),
	Opis nvarchar(1000),
	Trajanje int,
	Slika nvarchar(100),
)
go

create table FilmDjelatnik
(
	IDFilmDjelatnik int primary key identity,
	FilmId int foreign key references Film(IDFilm),
	OsobaID int foreign key references Osoba(IDOsoba)
)
go

create table ZanrFilm
(
	IDZanrFilm int primary key identity,
	FilmId int foreign key references Film(IDFilm),
	ZanrID int foreign key references Zanr(IDZanr)
)
