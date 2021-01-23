USE `poynac_website`;
INSERT INTO user (
	login,
	password,
	role
) VALUES (
	"admin",
	"admin", /* MD5 хэш пароля "admin" */
	2
);