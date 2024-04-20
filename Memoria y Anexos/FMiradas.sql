DECLARE @Sql NVARCHAR(500) DECLARE @Cursor CURSOR

SET @Cursor = CURSOR FAST_FORWARD FOR
SELECT DISTINCT sql = 'ALTER TABLE [' + tc2.TABLE_SCHEMA + '].[' +  tc2.TABLE_NAME + '] DROP [' + rc1.CONSTRAINT_NAME + '];'
FROM INFORMATION_SCHEMA.REFERENTIAL_CONSTRAINTS rc1
LEFT JOIN INFORMATION_SCHEMA.TABLE_CONSTRAINTS tc2 ON tc2.CONSTRAINT_NAME =rc1.CONSTRAINT_NAME

OPEN @Cursor FETCH NEXT FROM @Cursor INTO @Sql

WHILE (@@FETCH_STATUS = 0)
BEGIN
Exec sp_executesql @Sql
FETCH NEXT FROM @Cursor INTO @Sql
END

CLOSE @Cursor DEALLOCATE @Cursor
GO

EXEC sp_MSforeachtable 'DROP TABLE ?'
GO

USE fmiradas;

--2nd step: Create tables
CREATE TABLE users(
    emailUser VARCHAR(500) CHECK(emailUser LIKE '%_@__%.__%'),
	userType VARCHAR(50),
	passwordUser VARCHAR(500)NOT NULL,
	first_name VARCHAR(500)NOT NULL,
	last_name VARCHAR(500)NOT NULL,
	telephone INT
	PRIMARY KEY(emailUser,userType));
CREATE TABLE administrators(
    emailUser VARCHAR(500) PRIMARY KEY,
	userType VARCHAR(50) CHECK(userType='ADMIN'),
	FOREIGN KEY(emailUser,userType) REFERENCES users(emailUser,userType)
);

CREATE TABLE addresses(
	idAddress INT PRIMARY KEY,
	roadType VARCHAR(50) NOT NULL,
	nameSt VARCHAR(500) NOT NULL,
	numberSt INT NOT NULL,
	floorSt INT,
	apt VARCHAR(1),
	zipCode INT NOT NULL,
	city 	VARCHAR(500) NOT NULL,
	province VARCHAR(500) NOT NULL,
	region VARCHAR(500) NOT NULL,
	country VARCHAR(500) NOT NULL,
);

CREATE TABLE organizations(
    IdOrganization INT,
	orgType VARCHAR(50),
	illness VARCHAR(50),
	nameOrg VARCHAR(500) NOT NULL,
	idAddress INT NOT NULL,
	email VARCHAR(500) CHECK(email LIKE '%_@__%.__%'),
	telephone INT,
	information VARCHAR(900) NOT NULL,
	PRIMARY KEY(IdOrganization,orgType,illness),
    FOREIGN KEY(idAddress) REFERENCES addresses(idAddress)
);
CREATE TABLE centers(
	IdOrganization INT,
	orgType VARCHAR(50),
	illness VARCHAR(50),
	idCenter INT,
	centerDescription VARCHAR(500),
	idAddress INT,
	PRIMARY KEY(IdOrganization,orgType,illness,idCenter),
	FOREIGN KEY(IdOrganization,orgType,illness) REFERENCES organizations(IdOrganization,orgType,illness),
	FOREIGN KEY(idAddress) REFERENCES addresses(idAddress),
);
CREATE TABLE evaluatorOrganizations(
    IdOrganization INT,
	orgType VARCHAR(50) CHECK(orgType='EVALUATOR'),
	illness VARCHAR(50),
	PRIMARY KEY(IdOrganization,illness),
	FOREIGN KEY(IdOrganization,orgType,illness) REFERENCES organizations(IdOrganization,orgType,illness)
);
CREATE TABLE evaluatedOrganizations(
    IdOrganization INT,
	orgType VARCHAR(50) CHECK(orgType='EVALUATED'),
	illness VARCHAR(50),
	emailOrgPrincipal VARCHAR(500) NOT NULL ,
	emailOrgConsultant VARCHAR(500),
	PRIMARY KEY(IdOrganization,illness),
	FOREIGN KEY(IdOrganization,orgType,illness) REFERENCES organizations(IdOrganization,orgType,illness)
);
CREATE TABLE autisticEvaluatorOrganizations(
    IdOrganization INT PRIMARY KEY,
	illness VARCHAR(50) CHECK(illness='AUTISM'),
	FOREIGN KEY(IdOrganization,illness) REFERENCES evaluatorOrganizations(IdOrganization,illness)
);
CREATE TABLE autisticEvaluatedOrganizations(
    IdOrganization INT PRIMARY KEY,
	illness VARCHAR(50) CHECK(illness='AUTISM'),
	FOREIGN KEY(IdOrganization,illness) REFERENCES evaluatedOrganizations(IdOrganization,illness)
);
CREATE TABLE organizationUsers(
    emailUser VARCHAR(500),
	userType VARCHAR(50) CHECK(userType='ORGANIZATION'),
	idOrganization INT,
	orgType VARCHAR(50),
	illness VARCHAR(50),
	PRIMARY KEY(emailUser,idOrganization,orgType,illness),
	FOREIGN KEY(emailUser,userType) REFERENCES users(emailUser,userType),
	FOREIGN KEY(idOrganization,orgType,illness) REFERENCES organizations(idOrganization,orgType,illness)
);

CREATE TABLE evaluatorOrganizationUsers(
	emailUser VARCHAR(500),
	idOrganization INT,
	orgType VARCHAR(50) CHECK(orgType='EVALUATOR'),
	illness VARCHAR(50),
	PRIMARY KEY(emailUser,idOrganization,illness),
	FOREIGN KEY(emailUser,idOrganization,orgType,illness) REFERENCES organizationUsers(emailUser,idOrganization,orgType,illness),
	FOREIGN KEY(idOrganization,illness) REFERENCES evaluatorOrganizations(IdOrganization,illness)
);
CREATE TABLE evaluatedOrganizationUsers(
	emailUser VARCHAR(500),
	idOrganization INT,
	orgType VARCHAR(50) CHECK(orgType='EVALUATED'),
	illness VARCHAR(50),
	PRIMARY KEY(emailUser,idOrganization,illness),
	FOREIGN KEY(emailUser,idOrganization,orgType,illness) REFERENCES organizationUsers(emailUser,idOrganization,orgType,illness),
	FOREIGN KEY(idOrganization,illness) REFERENCES evaluatedOrganizations(IdOrganization,illness)
);

CREATE TABLE autisticEvaluatorOrgUsers(
	emailUser VARCHAR(500) PRIMARY KEY,
	idOrganization INT,
	illness VARCHAR(50) CHECK(illness='AUTISM'),
	FOREIGN KEY(emailUser,idOrganization,illness) REFERENCES evaluatorOrganizationUsers(emailUser,idOrganization,illness)
);

CREATE TABLE autisticEvaluatedOrgUsers(
	emailUser VARCHAR(500) PRIMARY KEY,
	idOrganization INT,
	illness VARCHAR(50) CHECK(illness='AUTISM'),
	FOREIGN KEY(emailUser,idOrganization,illness) REFERENCES evaluatedOrganizationUsers(emailUser,idOrganization,illness)
);
CREATE TABLE indicators (
	indicatorId INT,
	indicatorType VARCHAR(50),
	indicatorDescription VARCHAR(900) NOT NULL,
	indicatorPriority INT NOT NULL,
    PRIMARY KEY(indicatorId,indicatorType)
);
CREATE TABLE evidences(
	idEvidence INT,
	idIndicator INT,
    indicatorType VARCHAR(50),
	evidenceDescription VARCHAR(900) NOT NULL,
	evidenceValue INT NOT NULL,
	PRIMARY KEY(idEvidence,idIndicator,indicatorType),
	FOREIGN KEY(idIndicator,indicatorType) REFERENCES indicators(indicatorId,indicatorType)
);
CREATE TABLE evaluatorTeams(
    idEvaluatorTeam INT,
    creationDate DATE NOT NULL,
    emailConsultant VARCHAR(500),
    idOrganization INT,
	illness VARCHAR(50),
    PRIMARY KEY(idEvaluatorTeam,idOrganization,illness),
    FOREIGN KEY(emailConsultant,idOrganization,illness) REFERENCES evaluatorOrganizationUsers(emailUser,idOrganization,illness)
);
CREATE TABLE autisticEvaluatorTeams(
    idEvaluatorTeam INT,
	idOrganization INT,
	illness VARCHAR(50) CHECK(illness='AUTISM'),
    PRIMARY KEY(idEvaluatorTeam,idOrganization),
    FOREIGN KEY(idEvaluatorTeam,idOrganization,illness) REFERENCES evaluatorTeams(idEvaluatorTeam,idOrganization,illness)
);
CREATE TABLE evaluatorTeamMembers(
	emailUser VARCHAR(500),
    idEvaluatorTeam INT,
    idEvaluatorOrganization INT,
	idOrganization INT,
	illness VARCHAR(50),
	PRIMARY KEY(emailUser,idEvaluatorTeam,illness),
	FOREIGN KEY(emailUser,idOrganization,illness) REFERENCES evaluatorOrganizationUsers(emailUser,idOrganization,illness),
	FOREIGN KEY(idEvaluatorTeam,idEvaluatorOrganization,illness) REFERENCES evaluatorTeams(idEvaluatorTeam,idOrganization,illness)
);
CREATE TABLE autisticEvalTeamMembers(
	emailUser VARCHAR(500),
    idEvaluatorTeam INT,
	illness VARCHAR(50),
	PRIMARY KEY(emailUser,illness),
    FOREIGN KEY(emailUser,idEvaluatorTeam,illness) REFERENCES evaluatorTeamMembers(emailUser,idEvaluatorTeam,illness)
);
CREATE TABLE indicatorsEvaluations(
	evaluationDate DATE,
	evaluatedOrganizationId INT,
	evaluatorTeamId INT,
    evaluatorOrganizationId INT,
	illness VARCHAR(50),
	PRIMARY KEY(evaluationDate, evaluatedOrganizationId,illness),
    FOREIGN KEY(evaluatedOrganizationId, illness) REFERENCES evaluatedOrganizations(idOrganization,illness),
	FOREIGN KEY(evaluatorTeamId,evaluatorOrganizationId, illness) REFERENCES evaluatorTeams(idEvaluatorTeam,idOrganization,illness)
);
CREATE TABLE indicatorsEvaluationsRegs(
	evaluationDate DATE,
	evaluatedOrganizationId INT,
	illness VARCHAR(50),
	indicatorId INT,
	idEvidence INT,
	isMarked INT NOT NULL CHECK(isMarked=0 OR isMarked=1),
	PRIMARY KEY(evaluationDate, evaluatedOrganizationId,illness, indicatorId, idEvidence),
	FOREIGN KEY(evaluationDate, evaluatedOrganizationId,illness) REFERENCES indicatorsEvaluations(evaluationDate,evaluatedOrganizationId,illness),
	FOREIGN KEY(idEvidence,indicatorId,illness) REFERENCES evidences(idEvidence,idIndicator,indicatorType)
);

INSERT INTO indicators VALUES(1,'AUTISM','Existen programas de atenci�n sanitaria individualizados y actualizados',3);
INSERT INTO evidences VALUES(1,1,'AUTISM','Se dispone de un expediente m�dico individual, confidencial y actualizado, que contiene informaci�n referida a: medicaci�n, enfermedades, alergias, pruebas realizadas, historial',1);
INSERT INTO evidences VALUES(2,1,'AUTISM','Se realizan revisiones de seguimiento y prevenci�n peri�dicas, al menos una vez al a�o',1);
INSERT INTO evidences VALUES(3,1,'AUTISM','En los casos necesarios se desarrollan acciones de sensibilizaci�n, adaptaci�n al entorno sanitario y a las diferentes pruebas m�dicas',1);
INSERT INTO evidences VALUES(4,1,'AUTISM','Existen profesionales m�dicos de referencia, conocedores de las caracter�sticas del autismo, de distintas especialidades: medicina general, psiquiatr�a, odontolog�a',1);
INSERT INTO indicators VALUES(2,'AUTISM','Se garantiza la correcta administraci�n y seguimiento de los tratamientos farmacol�gicos',4);
INSERT INTO evidences VALUES(1,2,'AUTISM','Se realizan an�lisis peri�dicos de control y seguimiento de las medicaciones',1);
INSERT INTO evidences VALUES(2,2,'AUTISM','Existen registros que garantizan la correcta administraci�n de la medicaci�n y reflejan posibles incidencias',1);
INSERT INTO evidences VALUES(3,2,'AUTISM','Se registran, analizan y se informa a la familia y a los m�dicos que lo han prescrito, de los posibles efectos secundarios derivados de los cambios de medicaci�n',1);
INSERT INTO evidences VALUES(4,2,'AUTISM','Existe un protocolo-proceso de la administraci�n (responsable, control, autorizaciones ) conocido por todos',1);
INSERT INTO indicators VALUES(3,'AUTISM','Se contempla la intervenci�n individualizada en el �mbito del cuidado y autonom�a personal',1);
INSERT INTO evidences VALUES(1,3,'AUTISM','Cada plan de desarrollo individual contiene objetivos referidos al cuidado y autonom�a personal',1);
INSERT INTO evidences VALUES(2,3,'AUTISM','Existen momentos espec�ficos de intervenci�n en habilidades funcionales, referidas al cuidado y autonom�a personal, aprovechando contextos naturales',1);
INSERT INTO evidences VALUES(3,3,'AUTISM','Los profesionales que trabajan en contacto directo con las personas con TEA conocen y coordinan las pautas a seguir para procurar el bienestar f�sico de cada una de ellas, a trav�s de la promoci�n de habilidades referidas a la autonom�a personal: vestido, higiene, comida, autonom�a personal',1);
INSERT INTO evidences VALUES(4,3,'AUTISM','Las instalaciones cuentan con espacios suficientes y adecuados para la realizaci�n de actividades referidas al cuidado y la autonom�a personal: vestuarios, aseos, espacios que favorezcan la privacidad',1);
INSERT INTO indicators VALUES(4,'AUTISM','Se desarrollan actuaciones referidas a la seguridad e higiene en los diferentes �mbitos',1);
INSERT INTO evidences VALUES(1,4,'AUTISM','Existe un an�lisis previo, de forma individualizada, de diferentes situaciones, materiales o actividades que puedan implicar riesgo',1);
INSERT INTO evidences VALUES(2,4,'AUTISM','Existen ayudas t�cnicas y dispositivos de seguridad que, sin crear un entorno restrictivo, favorecen la autonom�a y la seguridad de las personas, minimizando los riesgos que pueden provenir del contexto',1);
INSERT INTO evidences VALUES(3,4,'AUTISM','Las instalaciones facilitan el desenvolvimiento aut�nomo: seguridad, ausencia de barreras, estructuraci�n de espacios',1);
INSERT INTO evidences VALUES(4,4,'AUTISM','Se dispone de protocolos de formaci�n e intervenci�n que permiten prevenir y abordar situaciones de emergencia de forma eficaz (planes de evacuaci�n, primeros auxilios)',1);
INSERT INTO indicators VALUES(5,'AUTISM','Se contemplan medidas preventivas individualizadas para mantener una salud adecuada',1);
INSERT INTO evidences VALUES(1,5,'AUTISM','Las condiciones f�sicas del entorno (mobiliario, actividades, puestos de trabajo) se adaptan a las caracter�sticas y necesidades ergon�micas de cada persona con TEA, promoviendo su bienestar f�sico: adecuada luz y temperatura, control postural, ruido ambiental',1);
INSERT INTO evidences VALUES(2,5,'AUTISM','Se promueve una nutrici�n adecuada (men�s adaptados, dietas, adecuaci�n a las posibilidades de degluci�n de cada persona)',1);
INSERT INTO evidences VALUES(3,5,'AUTISM','Cada persona participa en programas dirigidos a mantener una buena salud y prevenir un posible deterioro f�sico (control de peso, ejercicio f�sico, deporte, fisioterapia), con profesionales espec�ficos',1);
INSERT INTO evidences VALUES(4,5,'AUTISM','Se realizan programas de formaci�n para promover h�bitos de salud, referidos a habilidades de autonom�a, prevenci�n, sexualidad',1);
INSERT INTO indicators VALUES(6,'AUTISM','El ambiente promueve un estado emocional positivo en las Personas con TEA',4);
INSERT INTO evidences VALUES(1,6,'AUTISM','Se utilizan sistemas de estructuraci�n claros, sencillos y adaptados para que el ambiente sea predecible',1);
INSERT INTO evidences VALUES(2,6,'AUTISM','Existe un tratamiento individualizado de los problemas emocionales',1);
INSERT INTO evidences VALUES(3,6,'AUTISM','Cualquier nueva intervenci�n o tratamiento por parte de los profesionales, se pone en pr�ctica tras obtener el consentimiento informado de la persona con TEA o en su caso de las personas que la representen (familia, tutores)',1);
INSERT INTO evidences VALUES(4,6,'AUTISM','Se conocen y tienen en cuenta las afinidades personales y los gustos, para configurar grupos de compa�eros y planificar actividades',1);
INSERT INTO indicators VALUES(7,'AUTISM','Se promueve la m�xima estabilidad emocional en la vida de las personas con TEA',2);
INSERT INTO evidences VALUES(1,7,'AUTISM','Existe una estructura flexible de funcionamiento que permite resolver de forma inmediata los imprevistos que afectan a la estabilidad en la organizaci�n: ausencia de un profesional, cambios en las actividades previstas, alteraci�n de espacios',1);
INSERT INTO evidences VALUES(2,7,'AUTISM','Se minimizan las condiciones de estr�s que pueden afectar a la persona (informando por anticipado de los cambios de profesionales, horario y actividades)',1);
INSERT INTO evidences VALUES(3,7,'AUTISM','Se utilizan sistemas de estructuraci�n que facilitan la orientaci�n y el uso de los distintos espacios',1);
INSERT INTO evidences VALUES(4,7,'AUTISM','La persona con TEA tiene personas de referencia claras en su vida (familiares, profesionales, compa�eros, amigos), y se contempla la intervenci�n ante situaciones de ausencia (duelo, cambios de personal)',1);
INSERT INTO indicators VALUES(8,'AUTISM','Se desarrollan programas individualizados de intervenci�n conductual positiva',3);
INSERT INTO evidences VALUES(1,8,'AUTISM','Existen unas pautas generales de prevenci�n de conductas problem�ticas',1);
INSERT INTO evidences VALUES(2,8,'AUTISM','Se registran las conductas problem�ticas y se realiza un an�lisis funcional y contextual, previo al dise�o de estrategias de intervenci�n',1);
INSERT INTO evidences VALUES(3,8,'AUTISM','Se desarrollan estrategias de prevenci�n e intervenci�n ante conductas problem�ticas (resoluci�n de problemas, habilidades comunicativas, intervenci�n f�sica, estrategias de autocontrol) siempre basadas en par�metros �ticos y respetuosos con la persona',1);
INSERT INTO evidences VALUES(4,8,'AUTISM','Existe un protocolo de �intervenci�n f�sica� que asegura la intervenci�n adecuada en situaciones de conductas problem�ticas, con el objetivo de asegurar la salud y seguridad de la persona',1);
INSERT INTO indicators VALUES(9,'AUTISM','La persona con TEA y/o su representante participa en la planificaci�n, ejecuci�n y evaluaci�n de su plan de desarrollo individual',2);
INSERT INTO evidences VALUES(1,9,'AUTISM','Existen canales de participaci�n y expresi�n de las personas con TEA y/o sus representantes, en relaci�n con el Plan de Desarrollo Individual',1);
INSERT INTO evidences VALUES(2,9,'AUTISM','Se proporcionan apoyos para que las personas puedan llevar a cabo elecciones y decisiones',1);
INSERT INTO evidences VALUES(3,9,'AUTISM','Se analizan y tienen en cuenta los deseos, preferencias e intereses de las personas',1);
INSERT INTO evidences VALUES(4,9,'AUTISM','Las actividades se adaptan y estructuran de forma que se garantiza el �xito en su realizaci�n de la forma m�s aut�noma posible',1);
INSERT INTO indicators VALUES(10,'AUTISM','Las personas con TEA cuentan con apoyos personales individualizados',3);
INSERT INTO evidences VALUES(1,10,'AUTISM','Cada persona con TEA tiene un profesional, con afinidad, que le sirve de referencia y que realiza un seguimiento mas exhaustivo y cercano',1);
INSERT INTO evidences VALUES(2,10,'AUTISM','Cada persona tiene un grupo de compa�eros de referencia, aunque no est� en todo momento junto a ellos',1);
INSERT INTO evidences VALUES(3,10,'AUTISM','Las personas cuentan con el personal necesario en las actividades realizadas en el entorno de la comunidad',1);
INSERT INTO evidences VALUES(4,10,'AUTISM','Se promueve y apoya a las personas del entorno para que se impliquen como apoyos naturales de las personas con TEA',1);
INSERT INTO indicators VALUES(11,'AUTISM','Se respeta la intimidad',3);
INSERT INTO evidences VALUES(1,11,'AUTISM','Existen espacios, tiempos y pertenencias personalizadas',1);
INSERT INTO evidences VALUES(2,11,'AUTISM','Se respeta, o negocia en su caso, la decisi�n de la persona de utilizar su propio espacio, sus pertenencias y/o emplear libremente su tiempo cuando lo requiera',1);
INSERT INTO evidences VALUES(3,11,'AUTISM','Se favorece la intimidad en la realizaci�n de actividades referidas al aseo, vestido, cuidado personal',1);
INSERT INTO evidences VALUES(4,11,'AUTISM','El uso de la imagen e informaci�n sobre las personas con TEA est� sujeto a un protocolo de utilizaci�n y a la normativa sobre protecci�n de datos que garantiza el respeto y la confidencialidad',1);
INSERT INTO indicators VALUES(12,'AUTISM','Se promueven y respetan las pertenencias',2);
INSERT INTO evidences VALUES(1,12,'AUTISM','Los espacios y pertenencias personales son estables, est�n cuidados, y son adecuados a la edad cronol�gica, a las necesidades y preferencias de la persona',1);
INSERT INTO evidences VALUES(2,12,'AUTISM','Existe un protocolo que regula y garantiza la obtenci�n de refuerzos individualizados y pertenencias de libre elecci�n',1);
INSERT INTO evidences VALUES(3,12,'AUTISM','Cada persona recibe una contraprestaci�n real por su trabajo productivo',1);
INSERT INTO evidences VALUES(4,12,'AUTISM','Se promueven habilidades relacionadas con la gesti�n del dinero y pertenencias personales',1);
INSERT INTO indicators VALUES(13,'AUTISM','Se promueven las relaciones sociales significativas',3);
INSERT INTO evidences VALUES(1,13,'AUTISM','Se analizan y respetan las preferencias e incompatibilidades entre las personas con TEA para planificar y potenciar los grupos de compa�eros',1);
INSERT INTO evidences VALUES(2,13,'AUTISM','Existe un programa individualizado de inclusi�n social y laboral, que fomenta la interacci�n con compa�eros y personas sin discapacidad en los diferentes entornos',1);
INSERT INTO evidences VALUES(3,13,'AUTISM','Existe un programa que favorece las relaciones sociales: habilidades sociales y comunicativas, expresi�n de emociones, afectividad y sexualidad',1);
INSERT INTO evidences VALUES(4,13,'AUTISM','Se promueve y apoya a las personas del entorno para que se impliquen como apoyos naturales de las personas con TEA',1);
INSERT INTO indicators VALUES(14,'AUTISM','Se promueve el desarrollo de las capacidades e intereses individuales',4);
INSERT INTO evidences VALUES(1,14,'AUTISM','Se realiza una evaluaci�n de las habilidades, intereses y preferencias individuales de cada persona con TEA en los distintos �mbitos de desarrollo',1);
INSERT INTO evidences VALUES(2,14,'AUTISM','Los planes de desarrollo individualizados contemplan los intereses, las necesidades, los gustos y preferencias de la persona en todas las �reas de desarrollo, y en los distintos �mbitos y momentos',1);
INSERT INTO evidences VALUES(3,14,'AUTISM','Las actividades que se desarrollan en los distintos programas o servicios, se dise�an o seleccionan de forma que, adem�s de dar respuesta a las necesidades, intereses y capacidades personales, respondan a un criterio de funcionalidad',1);
INSERT INTO evidences VALUES(4,14,'AUTISM','En el �mbito de la inclusi�n social, cada persona tiene dise�ado un itinerario personalizado, planificado teniendo en cuenta sus capacidades e intereses, que abarca todos los pasos del proceso: evaluaci�n funcional, b�squeda de recursos, formaci�n, adaptaci�n�',1);
INSERT INTO indicators VALUES(15,'AUTISM','Se promueve el avance y el desarrollo continuo de la persona',3);
INSERT INTO evidences VALUES(1,15,'AUTISM','Se utilizan adaptaciones t�cnicas, apoyos visuales... en funci�n de las necesidades de cada persona',1);
INSERT INTO evidences VALUES(2,15,'AUTISM','Se dise�an materiales, programas y actividades acordes con un criterio evolutivo y de funcionalidad',1);
INSERT INTO evidences VALUES(3,15,'AUTISM','Existe un sistema de evaluaci�n de habilidades sociales, laborales,... que permite contrastar el avance a lo largo del tiempo',1);
INSERT INTO evidences VALUES(4,15,'AUTISM','Se planifica y promueve la retirada gradual de apoyos',1);
INSERT INTO indicators VALUES(16,'AUTISM','Se garantiza el respeto a la identidad y dignidad de la persona',4);
INSERT INTO evidences VALUES(1,16,'AUTISM','Existen unas normas de organizaci�n y funcionamiento interno en las que se recogen derechos y deberes referidos a profesionales, familias, organizaci�n y personas con TEA, que tienen como referencia la Declaraci�n Universal de los Derechos Humanos y, de forma especial, la Carta de los Derechos de las Personas con Autismo',1);
INSERT INTO evidences VALUES(2,16,'AUTISM','Existe un c�digo de �tica profesional, consensuado entre familias y profesionales, que define como deben ser las relaciones, las actuaciones y actitudes hacia las personas con TEA',1);
INSERT INTO evidences VALUES(3,16,'AUTISM','La Organizaci�n defiende los derechos de las personas con TEA',1);
INSERT INTO evidences VALUES(4,16,'AUTISM','La Organizaci�n no es restrictiva, fomenta nuevas oportunidades, no coarta las posibilidades de elecci�n ni de desarrollo de las personas',1);
INSERT INTO indicators VALUES(17,'AUTISM','Se garantiza la integridad f�sica',4);
INSERT INTO evidences VALUES(1,17,'AUTISM','No se utiliza ning�n tipo de restricci�n f�sica ni tratamiento farmacol�gico si no est� previamente consensuado con la familia, un t�cnico cualificado y, en los casos en que sea posible, con la propia persona con TEA, y en caso de f�rmacos �stos deben haber sido siempre prescritos por los m�dicos',1);
INSERT INTO evidences VALUES(2,17,'AUTISM','Se analizan, por anticipado, los posibles riesgos derivados de la programaci�n de una nueva actividad',1);
INSERT INTO evidences VALUES(3,17,'AUTISM','Existen pautas y medidas para controlar posibles abusos f�sicos y/o emocionales',1);
INSERT INTO evidences VALUES(4,17,'AUTISM','Se utilizan adaptaciones t�cnicas y dispositivos de seguridad que minimizan los riesgos de da�os f�sicos referidos a la utilizaci�n y el uso de espacios, herramientas, m�quinas y materiales',1);
INSERT INTO indicators VALUES(18,'AUTISM','Las personas reciben formaci�n variada y adaptada, previa a la emisi�n de conductas de autodeterminaci�n',1);
INSERT INTO evidences VALUES(1,18,'AUTISM','Se apoya el que la persona comprenda y planifique la secuencia de pasos de las actividades que realiza',1);
INSERT INTO evidences VALUES(2,18,'AUTISM','Se utilizan apoyos para favorecer el acceso de las personas con TEA a la informaci�n y toma de decisiones (pictogramas...)',1);
INSERT INTO evidences VALUES(3,18,'AUTISM','Se planifican actividades que permiten poner en pr�ctica las habilidades adquiridas  relacionadas con la conducta de autodeterminaci�n',1);
INSERT INTO evidences VALUES(4,18,'AUTISM','Se revisan y actualizan peri�dicamente las actividades, apoyos y materiales planificados, para poder readecuarlos a las necesidades cambiantes de las personas con TEA, o se estudian nuevas alternativas',1);
INSERT INTO indicators VALUES(19,'AUTISM','Las personas participan en la planificaci�n y ejercen un control sobre su vida',4);
INSERT INTO evidences VALUES(1,19,'AUTISM','Se anticipan y planifican las actividades a realizar, disponiendo toda persona de momentos en los que puede elegir libremente qu� hacer o no hacer en su tiempo libre',1);
INSERT INTO evidences VALUES(2,19,'AUTISM','La persona tiene oportunidades, y dispone de un sistema de comunicaci�n adaptado, para comunicar necesidades, emociones, y para realizar elecciones',1);
INSERT INTO evidences VALUES(3,19,'AUTISM','Se promueven actividades encaminadas a desarrollar capacidades de planificaci�n que permitan a la persona elegir o participar en las decisiones que afectan a su vida, tanto en lo referido a cuestiones cotidianas como a cuestiones de mayor trascendencia para su futuro',1);
INSERT INTO evidences VALUES(4,19,'AUTISM','Se aprovechan o provocan situaciones controladas o riesgos asumibles (posibles imprevistos, situaciones en las que es necesario pedir ayuda...), que impliquen habilidades de resoluci�n de problemas en distintos contextos cotidianos, se trabajan estrategias y se ofrecen apoyos a la persona para su resoluci�n.',1);
INSERT INTO indicators VALUES(20,'AUTISM','Se promueve la inclusi�n social de las personas con TEA',4);
INSERT INTO evidences VALUES(1,20,'AUTISM','Se realiza un an�lisis ecol�gico y funcional previo a la inclusi�n social de la persona',1);
INSERT INTO evidences VALUES(2,20,'AUTISM','La persona participa en actividades y programas realizados en distintos entornos comunitarios',1);
INSERT INTO evidences VALUES(3,20,'AUTISM','Se facilita y apoya la inclusi�n en recursos y actividades comunitarias',1);
INSERT INTO evidences VALUES(4,20,'AUTISM','Se utilizan los medios de comunicaci�n convencionales, para la informaci�n y divulgaci�n hacia la sociedad',1);
INSERT INTO indicators VALUES(21,'AUTISM','Las actuaciones con la persona con TEA tienen en cuenta las expectativas de la familia',1);
INSERT INTO evidences VALUES(1,21,'AUTISM','Existen procedimientos para recoger y revisar peri�dicamente las expectativas de la familia hacia la persona con TEA y hacia la Organizaci�n',1);
INSERT INTO evidences VALUES(2,21,'AUTISM','El Plan de Desarrollo Personal integra las expectativas de la familia que se ajustan a las necesidades y capacidades de las personas con TEA',1);
INSERT INTO evidences VALUES(3,21,'AUTISM','La Organizaci�n promueve el que la familia se integre activamente en la red de apoyos de la persona con TEA',1);
INSERT INTO evidences VALUES(4,21,'AUTISM','Los objetivos del Plan de Desarrollo Personal respetan el estilo de vida y de relaci�n familiar de la persona con TEA',1);
INSERT INTO indicators VALUES(22,'AUTISM','Se facilita la implicaci�n de las familias en la Organizaci�n',1);
INSERT INTO evidences VALUES(1,22,'AUTISM','Las familias participan en la elaboraci�n de los Planes de Desarrollo Personal de la persona con TEA, y pueden tener informaci�n de su evoluci�n en cualquier momento',1);
INSERT INTO evidences VALUES(2,22,'AUTISM','Existe un plan de informaci�n, formaci�n y asesoramiento a las familias, con profesionales responsables que las conocen',1);
INSERT INTO evidences VALUES(3,22,'AUTISM','Existe una variedad de v�as de implicaci�n y participaci�n en la Organizaci�n',1);
INSERT INTO evidences VALUES(4,22,'AUTISM','Existen diferentes v�as de comunicaci�n / coordinaci�n con los servicios y profesionales',1);
INSERT INTO indicators VALUES(23,'AUTISM','Se favorece un aumento del nivel de satisfacci�n en las familias',1);
INSERT INTO evidences VALUES(1,23,'AUTISM','Existen v�as para medir y analizar el nivel de satisfacci�n: encuestas, entrevistas personales',1);
INSERT INTO evidences VALUES(2,23,'AUTISM','Existen v�as para comunicar incidencias, realizar sugerencias y propuestas de mejora',1);
INSERT INTO evidences VALUES(3,23,'AUTISM','Se analizan y tienen en cuenta las incidencias y propuestas formuladas por las familias',1);
INSERT INTO evidences VALUES(4,23,'AUTISM','Se implica a las familias en los procesos de mejora',1);
INSERT INTO indicators VALUES(24,'AUTISM','Se conocen, valoran y se tienen en cuenta las propuestas e iniciativas provenientes de los profesionales',1);
INSERT INTO evidences VALUES(1,24,'AUTISM','Existe f�cil acceso por parte de los profesionales hacia el equipo directivo',1);
INSERT INTO evidences VALUES(2,24,'AUTISM','Se registran y valoran las propuestas de intervenci�n / organizaci�n provenientes de los profesionales',1);
INSERT INTO evidences VALUES(3,24,'AUTISM','Se promueve el desarrollo profesional a trav�s del ajuste de los puestos de trabajo a las expectativas profesionales',1);
INSERT INTO evidences VALUES(4,24,'AUTISM','Se solicitan aportaciones de los profesionales referidas a distintos proyectos de la entidad',1);
INSERT INTO indicators VALUES(25,'AUTISM','Las responsabilidades de los profesionales son coherentes con sus funciones',1);
INSERT INTO evidences VALUES(1,25,'AUTISM','Existe un documento que especifica la estructura del personal de la Organizaci�n, as� como las funciones de cada uno de los profesionales',1);
INSERT INTO evidences VALUES(2,25,'AUTISM','Cada profesional conoce sus funciones',1);
INSERT INTO evidences VALUES(3,25,'AUTISM','Las funciones se adecuan a su perfil profesional',1);
INSERT INTO evidences VALUES(4,25,'AUTISM','Las responsabilidades se ajustan a las funciones',1);
INSERT INTO indicators VALUES(26,'AUTISM','Se promueve la participaci�n y el trabajo en equipo',4);
INSERT INTO evidences VALUES(1,26,'AUTISM','La estructura de la Organizaci�n contempla el funcionamiento a trav�s de equipos de trabajo',1);
INSERT INTO evidences VALUES(2,26,'AUTISM','Existen grupos de mejora para evaluar peri�dicamente y hacer propuestas de mejora en la Organizaci�n',1);
INSERT INTO evidences VALUES(3,26,'AUTISM','Existen oportunidades de abordar en grupo estrategias puntuales de intervenci�n, o de apoyo a la intervenci�n, planteadas por cualquier profesional',1);
INSERT INTO evidences VALUES(4,26,'AUTISM','La direcci�n de la Organizaci�n promueve la estabilidad del equipo de profesionales',1);
INSERT INTO indicators VALUES(27,'AUTISM','Se favorece un aumento del nivel de satisfacci�n en los profesionales',1);
INSERT INTO evidences VALUES(1,27,'AUTISM','Existen v�as para analizar el nivel de satisfacci�n y motivaci�n profesional: encuestas, entrevistas personales',1);
INSERT INTO evidences VALUES(2,27,'AUTISM','Se reconocen, valoran y difunden las Buenas Pr�cticas Profesionales desarrolladas en la Organizaci�n',1);
INSERT INTO evidences VALUES(3,27,'AUTISM','Adem�s del aseguramiento de los m�nimos legales referidos a contrataci�n, remuneraci�n, seguridad, la Organizaci�n valora y promueve las cuestiones que inciden de forma espec�fica en la satisfacci�n y motivaci�n de cada profesional',1);
INSERT INTO evidences VALUES(4,27,'AUTISM','Se planifican estrategias a largo plazo, que inciden directamente sobre la satisfacci�n de los profesionales',1);
INSERT INTO indicators VALUES(28,'AUTISM','Los profesionales est�n implicados en la Organizaci�n',1);
INSERT INTO evidences VALUES(1,28,'AUTISM','Se implica a todo el personal en los procesos de mejora',1);
INSERT INTO evidences VALUES(2,28,'AUTISM','Los profesionales, junto a la entidad titular, participan en la toma de decisiones organizativas y/o de planificaci�n',1);
INSERT INTO evidences VALUES(3,28,'AUTISM','Existe informaci�n sobre los proyectos de la Organizaci�n',1);
INSERT INTO evidences VALUES(4,28,'AUTISM','Existen posibilidades de implicaci�n en los proyectos de la Organizaci�n',1);
INSERT INTO indicators VALUES(29,'AUTISM','Se eval�an las necesidades y deseos de las personas con TEA en los distintos �mbitos de intervenci�n',4);
INSERT INTO evidences VALUES(1,29,'AUTISM','Existe un sistema de recogida de datos sobre las necesidades de la persona con TEA en los distintos �mbitos de intervenci�n',1);
INSERT INTO evidences VALUES(2,29,'AUTISM','Para la realizaci�n de cada Plan de Desarrollo Personal, se realiza una revisi�n de informes y evaluaciones previas en la que intervienen profesionales y personas del entorno socio-familiar.',1);
INSERT INTO evidences VALUES(3,29,'AUTISM','En la valoraci�n de las necesidades participan todos los profesionales y se implica a la familia, que tiene la posibilidad de plantear qu� objetivos considera m�s necesarios en la formaci�n de sus hijos o hijas',1);
INSERT INTO evidences VALUES(4,29,'AUTISM','En la valoraci�n de las necesidades se implica a la persona con TEA a trav�s de distintas modalidades comunicativas o apoyos personalizados',1);
INSERT INTO indicators VALUES(30,'AUTISM','Se elaboran planes de intervenci�n adaptados a las necesidades espec�ficas a lo largo de toda la vida',3);
INSERT INTO evidences VALUES(1,30,'AUTISM','Se elaboran planes individualizados de intervenci�n ante conductas problem�ticas o necesidades espec�ficas',1);
INSERT INTO evidences VALUES(2,30,'AUTISM','Los planes espec�ficos de intervenci�n se consens�an entre todos los profesionales que est�n en contacto con la persona con TEA, con su familia, y con la propia persona con TEA, siempre que sea posible, a trav�s de distintas v�as o instrumentos adaptados',1);
INSERT INTO evidences VALUES(3,30,'AUTISM','Las personas implicadas en prestar apoyo a las personas con TEA est�n coordinadas en el uso de pautas espec�ficas de intervenci�n en diferentes �mbitos (conducta, rehabilitaci�n funcional motora, correcci�n postural, comunicaci�n)',1);
INSERT INTO evidences VALUES(4,30,'AUTISM','Las actividades se adaptan y estructuran de forma que se garantice el �xito y su realizaci�n de la forma m�s aut�noma posible',1);
INSERT INTO indicators VALUES(31,'AUTISM','La estructura de la Programaci�n General de la Organizaci�n se adapta a las caracter�sticas de las personas con TEA',2);
INSERT INTO evidences VALUES(1,31,'AUTISM','Existe una Programaci�n General que engloba todos los �mbitos de intervenci�n y que sirve de referente para realizar los Planes de Desarrollo Personal',1);
INSERT INTO evidences VALUES(2,31,'AUTISM','Los contenidos de la Programaci�n General se eval�an peri�dicamente, y se modifican si se considera necesario',1);
INSERT INTO evidences VALUES(3,31,'AUTISM','Los objetivos de trabajo que promueve cada Plan de Desarrollo Personal son operativos',1);
INSERT INTO evidences VALUES(4,31,'AUTISM','Existe un an�lisis que evidencia la funcionalidad de los objetivos y aprendizajes programados',1);
INSERT INTO indicators VALUES(32,'AUTISM','Se adecua el proceso de elaboraci�n de los Planes de Desarrollo Personal a las caracter�sticas de las personas con TEA',4);
INSERT INTO evidences VALUES(1,32,'AUTISM','Existe un proceso de elaboraci�n de los Planes de Desarrollo Personal en el que participan todos los profesionales que est�n en contacto con la persona con TEA, la familia, y la propia persona con TEA, siempre que sea posible, a trav�s de distintas v�as o instrumentos adaptados',1);
INSERT INTO evidences VALUES(2,32,'AUTISM','El Plan de Desarrollo Personal detalla los objetivos y metas, y los apoyos necesarios para su consecuci�n',1);
INSERT INTO evidences VALUES(3,32,'AUTISM','Se planifican revisiones peri�dicas tanto de los objetivos como de las necesidades de apoyo de cada persona con TEA',1);
INSERT INTO evidences VALUES(4,32,'AUTISM','Existe flexibilidad y posibilidad de introducir nuevos objetivos o metas y/o modificar el tipo o grado de apoyo, cuando el Plan de Desarrollo Personal ya est� en marcha',1);
INSERT INTO indicators VALUES(33,'AUTISM','Existe un proceso de planificaci�n y captaci�n de apoyos que responde a las caracter�sticas de cada persona',3);
INSERT INTO evidences VALUES(1,33,'AUTISM','La planificaci�n de apoyos tiene en cuenta las aportaciones de la propia persona con TEA, siempre que sea posible, y de las personas de referencia en sus diferentes contextos vitales (familias, profesionales, amigos, conocidos)',1);
INSERT INTO evidences VALUES(2,33,'AUTISM','Se promueve la captaci�n de apoyos naturales',1);
INSERT INTO evidences VALUES(3,33,'AUTISM','La definici�n de apoyos tiene en cuenta el que la persona pueda conseguir sus objetivos y metas en los distintos �mbitos y contextos vitales',1);
INSERT INTO evidences VALUES(4,33,'AUTISM','Se dota a la persona de estrategias y habilidades que le permitan ejercer cambios y un control del entorno (elecciones, expresi�n de necesidades, resoluci�n de problemas)',1);
INSERT INTO indicators VALUES(34,'AUTISM','Los criterios metodol�gicos se adaptan a las necesidades y capacidades de la persona con TEA',2);
INSERT INTO evidences VALUES(1,34,'AUTISM','Existe unidad de criterios en los distintos servicios, programas o contextos donde participa la persona',1);
INSERT INTO evidences VALUES(2,34,'AUTISM','La definici�n y especificaci�n de objetivos o metas personales facilita la interpretaci�n objetiva, tanto en su ejecuci�n como en su evaluaci�n por parte de todos los profesionales',1);
INSERT INTO evidences VALUES(3,34,'AUTISM','Se tiene en cuenta la generalizaci�n de aprendizajes',1);
INSERT INTO evidences VALUES(4,34,'AUTISM','Los objetivos o metas permiten planificar nuevos aprendizajes',1);
INSERT INTO indicators VALUES(35,'AUTISM','Los Planes de Desarrollo Personal se adaptan a la persona',3);
INSERT INTO evidences VALUES(1,35,'AUTISM','Se abordan todas las necesidades en las diferentes �reas de desarrollo personal y social de la persona con TEA',1);
INSERT INTO evidences VALUES(2,35,'AUTISM','El Plan de Desarrollo Personal favorece el que la persona con TEA participe y realice actividades adaptadas a sus gustos y capacidades, en distintos contextos, favoreciendo siempre la mayor inclusi�n posible',1);
INSERT INTO evidences VALUES(3,35,'AUTISM','Se contemplan diferentes opciones, adecuadas a los diferentes niveles de adaptaci�n y capacidades',1);
INSERT INTO evidences VALUES(4,35,'AUTISM','Existen programas en funci�n de las diferentes etapas evolutivas (escolares, adultos...)',1);
INSERT INTO indicators VALUES(36,'AUTISM','Existe un seguimiento y evaluaci�n continua de cada Plan de Desarrollo Personal',3);
INSERT INTO evidences VALUES(1,36,'AUTISM','Existen informes de evaluaci�n individual de cada persona con TEA',1);
INSERT INTO evidences VALUES(2,36,'AUTISM','Se realizan orientaciones y propuestas de intervenci�n futura (Plan de Desarrollo Futuro Personal) basadas en la evaluaci�n',1);
INSERT INTO evidences VALUES(3,36,'AUTISM','Existen instrumentos de evaluaci�n basados en las dimensiones de calidad de vida',1);
INSERT INTO evidences VALUES(4,36,'AUTISM','Los resultados de la evaluaci�n y el seguimiento provienen tanto de todos los profesionales que est�n en contacto con la persona con TEA, como de su familia, conocidos y otras personas de referencia en su vida',1);
INSERT INTO indicators VALUES(37,'AUTISM','Se asegura una formaci�n inicial a los nuevos profesionales',2);
INSERT INTO evidences VALUES(1,37,'AUTISM','Existe un procedimiento de informaci�n, formaci�n y apoyo a nuevos profesionales, personas que prestan apoyo natural, voluntarios, alumnos en pr�cticas',1);
INSERT INTO evidences VALUES(2,37,'AUTISM','Se organizan acciones formativas en las que participan los nuevos profesionales, voluntarios, alumnos en pr�cticas',1);
INSERT INTO evidences VALUES(3,37,'AUTISM','Existe una documentaci�n formativa inicial que contiene informaci�n sobre autismo, programas y pautas de intervenci�n',1);
INSERT INTO evidences VALUES(4,37,'AUTISM','Cada nuevo profesional tiene asignado un profesional-tutor que se responsabiliza de su formaci�n y seguimiento, y una ficha personal de formaci�n donde se recoge toda su trayectoria formativa y profesional',1);
INSERT INTO indicators VALUES(38,'AUTISM','La formaci�n incluye aspectos t�cnicos, organizacionales y valores de la organizaci�n',1);
INSERT INTO evidences VALUES(1,38,'AUTISM','Existe un documento sobre la naturaleza, finalidad, valores, objetivos y principios �ticos de la organizaci�n difundido a todos los profesionales',1);
INSERT INTO evidences VALUES(2,38,'AUTISM','Todos los profesionales tienen acceso a la pol�tica de la Organizaci�n, los planes de mejora y el sistema de gesti�n de calidad',1);
INSERT INTO evidences VALUES(3,38,'AUTISM','Existen canales de informaci�n para transmitir los fines, valores y objetivos de la Organizaci�n',1);
INSERT INTO evidences VALUES(4,38,'AUTISM','Se ofrece informaci�n peri�dica sobre los proyectos y trayectoria de la Organizaci�n',1);
INSERT INTO indicators VALUES(39,'AUTISM','Cada profesional recibe una formaci�n espec�fica sobre su puesto de trabajo',2);
INSERT INTO evidences VALUES(1,39,'AUTISM','El plan de formaci�n incluye acciones espec�ficas para puestos de trabajo concretos',1);
INSERT INTO evidences VALUES(2,39,'AUTISM','Todos los profesionales en su proceso de formaci�n inicial reciben una formaci�n espec�fica referida a su puesto de trabajo, en la que se promueve la participaci�n activa y el aprendizaje significativo',1);
INSERT INTO evidences VALUES(3,39,'AUTISM','Todos los profesionales tienen posibilidades, de forma peri�dica, de actualizar o ampliar la formaci�n referida a su puesto de trabajo',1);
INSERT INTO evidences VALUES(4,39,'AUTISM','La Organizaci�n promueve el que se realicen proyectos o iniciativas encaminadas a la formaci�n espec�fica o especializaci�n: intercambios profesionales, grupos de trabajo espec�ficos',1);
INSERT INTO indicators VALUES(40,'AUTISM','Se promueve la formaci�n continua, la actualizaci�n y el desarrollo profesional',2);
INSERT INTO evidences VALUES(1,40,'AUTISM','Existe un plan de formaci�n anual en el que se incluyen necesidades y demandas planteadas por los profesionales',1);
INSERT INTO evidences VALUES(2,40,'AUTISM','La Organizaci�n colabora con otras entidades (Universidad), en proyectos de investigaci�n y avance cient�fico',1);
INSERT INTO evidences VALUES(3,40,'AUTISM','Todos los profesionales participan de forma peri�dica en acciones de formaci�n realizadas por la propia entidad o por otras Organizaciones',1);
INSERT INTO evidences VALUES(4,40,'AUTISM','Existe un procedimiento que eval�a peri�dicamente el desempe�o profesional',1);
INSERT INTO indicators VALUES(41,'AUTISM','La entidad cuenta con recursos propios que favorecen la formaci�n, actualizaci�n y desarrollo profesional',1);
INSERT INTO evidences VALUES(1,41,'AUTISM','Existe un programa de formaci�n interna (cursos, reuniones de formaci�n, intercambios con otras organizaciones...)',1);
INSERT INTO evidences VALUES(2,41,'AUTISM','Existe posibilidad de acceso a v�as de actualizaci�n: nuevas publicaciones espec�ficas, investigaci�n, bibliograf�a actualizada, Internet',1);
INSERT INTO evidences VALUES(3,41,'AUTISM','Existe un sistema de gesti�n del conocimiento por el que toda la formaci�n e informaci�n recibida por un profesional se difunde y se hace accesible al resto del equipo',1);
INSERT INTO evidences VALUES(4,41,'AUTISM','La Organizaci�n mide el impacto de la formaci�n profesional, recogiendo indicios de c�mo la formaci�n recibida por los profesionales produce cambios organizativos',1);
INSERT INTO indicators VALUES(42,'AUTISM','La actuaci�n de cada profesional tiene como referencia los principios de la Planificaci�n Centrada en la Persona',4);
INSERT INTO evidences VALUES(1,42,'AUTISM','El profesional promueve un conocimiento profundo de la persona con TEA, sus expectativas, metas en los distintos contextos vitales',1);
INSERT INTO evidences VALUES(2,42,'AUTISM','El profesional impulsa la creaci�n de C�rculos de Apoyo que puedan implicar al resto de profesionales, familias, amigos para definir y poner en marcha el Plan de Desarrollo Personal',1);
INSERT INTO evidences VALUES(3,42,'AUTISM','El profesional coordina y motiva la funci�n que cada persona del C�rculo de Apoyo ha asumido',1);
INSERT INTO evidences VALUES(4,42,'AUTISM','El profesional promueve la captaci�n de apoyos, especialmente naturales, para facilitar el Plan de Desarrollo Personal',1);
INSERT INTO indicators VALUES(43,'AUTISM','Existe una informaci�n individualizada de cada persona con TEA',4);
INSERT INTO evidences VALUES(1,43,'AUTISM','Se utilizan instrumentos que permiten obtener datos significativos para la elaboraci�n de cada perfil personal, incidiendo en preferencias, necesidades, visi�n de futuro',1);
INSERT INTO evidences VALUES(2,43,'AUTISM','Existe documentaci�n personalizada (pautas espec�ficas, gustos e intereses, capacidades, mapa de relaciones sociales...)',1);
INSERT INTO evidences VALUES(3,43,'AUTISM','Existe un proceso que facilita el conocimiento de la persona con TEA y que contempla, tanto la informaci�n que �sta nos pueda aportar de forma directa sobre su vida y expectativas de futuro, como la que puedan aportarnos las personas mas significativas de sus contextos vitales (profesionales, familia, amigos)',1);
INSERT INTO evidences VALUES(4,43,'AUTISM','Existen fichas personales de registro y seguimiento de las habilidades y metas que se trabajan con cada persona',1);
INSERT INTO indicators VALUES(44,'AUTISM','Se conoce en profundidad y de forma integral a la persona con TEA',3);
INSERT INTO evidences VALUES(1,44,'AUTISM','Todos los profesionales conocen la informaci�n b�sica detallada referida a cada persona con TEA (pautas de intervenci�n, gustos e intereses, nivel de capacidades...), y participan en el proceso de revisi�n y actualizaci�n de la misma',1);
INSERT INTO evidences VALUES(2,44,'AUTISM','Se utilizan instrumentos que permiten obtener datos significativos para la elaboraci�n de cada Plan de Desarrollo Personal, incidiendo en preferencias, necesidades, visi�n de futuro',1);
INSERT INTO evidences VALUES(3,44,'AUTISM','Se obtiene informaci�n directa de las personas con TEA y de las de personas cercanas y que comparten experiencias vitales con ella: familias, profesionales, conocidos',1);
INSERT INTO evidences VALUES(4,44,'AUTISM','La informaci�n de la que se dispone se refiere a todas las �reas y �mbitos vitales de la persona, y tiene en cuenta capacidades, gustos e intereses, necesidades y apoyos que requiere, pautas espec�ficas de intervenci�n, aspectos que le desagradan',1);
INSERT INTO indicators VALUES(45,'AUTISM','La intervenci�n es adaptada a cada Plan de Desarrollo Personal (caracter�sticas, necesidades, deseos, preferencias de la persona)',3);
INSERT INTO evidences VALUES(1,45,'AUTISM','Las posibilidades de elecci�n que tienen las personas con TEA tienen en cuenta sus gustos, intereses, visi�n de futuro',1);
INSERT INTO evidences VALUES(2,45,'AUTISM','Los objetivos que se plantean con cada persona con TEA son funcionales, tienen un impacto positivo en su Plan de Desarrollo Personal',1);
INSERT INTO evidences VALUES(3,45,'AUTISM','Las actividades, interacci�n y materiales est�n adaptados a las capacidades y edad cronol�gica',1);
INSERT INTO evidences VALUES(4,45,'AUTISM','Se tienen en cuenta las necesidades individuales de apoyo espec�fico y/o especializado',1);
INSERT INTO indicators VALUES(46,'AUTISM','La pr�ctica y actitudes profesionales tienen como referente la misi�n y los valores de la organizaci�n',2);
INSERT INTO evidences VALUES(1,46,'AUTISM','Todos los profesionales conocen y comparten la misi�n y los valores de la Organizaci�n',1);
INSERT INTO evidences VALUES(2,46,'AUTISM','La Organizaci�n cuenta con un C�digo �tico que concreta su misi�n/visi�n y define los valores y principios que deben presidir la pr�ctica profesional',1);
INSERT INTO evidences VALUES(3,46,'AUTISM','Todos los profesionales conocen, aceptan y se comprometen a regular su pr�ctica bas�ndose en el C�digo �tico',1);
INSERT INTO evidences VALUES(4,46,'AUTISM','Existe un sistema de reflexi�n que persigue integrar en la pr�ctica profesional los valores y actitudes consensuados en el C�digo �tico y evaluar peri�dicamente en que medida esto es as�',1);
INSERT INTO indicators VALUES(47,'AUTISM','La Organizaci�n promueve la ampliaci�n del conocimiento a trav�s de la participaci�n activa en investigaciones sobre TEA',2);
INSERT INTO evidences VALUES(1,47,'AUTISM','Se mantienen convenios de colaboraci�n con instituciones universitarias y cient�ficas que realizan investigaci�n sobre TEA',1);
INSERT INTO evidences VALUES(2,47,'AUTISM','La Organizaci�n promueve investigaciones sobre TEA basadas en protocolos definidos en gu�as de buenas pr�cticas',1);
INSERT INTO evidences VALUES(3,47,'AUTISM','La Organizaci�n colabora en investigaciones sobre TEA.',1);
INSERT INTO evidences VALUES(4,47,'AUTISM','Se aplican o incorporan en la pr�ctica los resultados de investigaciones cient�ficamente contrastadas',1);
INSERT INTO indicators VALUES(48,'AUTISM','La configuraci�n de los grupos de compa�eros se adaptan a las necesidades de las personas con TEA',1);
INSERT INTO evidences VALUES(1,48,'AUTISM','Existen criterios que justifican los grupos de compa�eros en los que se integran las personas con TEA: edad, capacidades, preferencias',1);
INSERT INTO evidences VALUES(2,48,'AUTISM','Se analiza peri�dicamente la interacci�n entre los componentes de los agrupamientos',1);
INSERT INTO evidences VALUES(3,48,'AUTISM','Se detectan y resuelven los posibles conflictos y/o incompatibilidades detectadas',1);
INSERT INTO evidences VALUES(4,48,'AUTISM','Existe flexibilidad, favoreciendo la elecci�n, para realizar nuevos grupos de compa�eros ante situaciones o actividades puntuales o imprevistos',1);
INSERT INTO indicators VALUES(49,'AUTISM','Los profesionales son una referencia clara para las personas con TEA',2);
INSERT INTO evidences VALUES(1,49,'AUTISM','Cada persona con TEA dispone de un profesional-tutor de referencia',1);
INSERT INTO evidences VALUES(2,49,'AUTISM','Se analiza peri�dicamente la relaci�n y la adecuaci�n del perfil humano y profesional a las caracter�sticas y preferencias de la persona con TEA, existiendo posibilidad de cambio de profesional de referencia',1);
INSERT INTO evidences VALUES(3,49,'AUTISM','Existen unos criterios de asignaci�n de los profesionales a la persona con TEA',1);
INSERT INTO evidences VALUES(4,49,'AUTISM','Los profesionales-tutores canalizan toda la informaci�n pertinente sobre la persona con TEA, y coordinan las intervenciones y la prestaci�n de apoyos en los distintos contextos',1);
INSERT INTO indicators VALUES(50,'AUTISM','Existe una organizaci�n de las tareas y actividades',3);
INSERT INTO evidences VALUES(1,50,'AUTISM','Existe una amplia variedad de tareas y actividades que responde a criterios de funcionalidad, significatividad, motivacionales y formativos',1);
INSERT INTO evidences VALUES(2,50,'AUTISM','Las actividades abarcan tanto los diferentes niveles aptitudinales como los intereses de las personas que participan en ellas',1);
INSERT INTO evidences VALUES(3,50,'AUTISM','Se estructuran los distintos procesos de trabajo asignando a cada persona responsabilidades dentro de los mismos',1);
INSERT INTO evidences VALUES(4,50,'AUTISM','Existe una secuenciaci�n de los pasos de las actividades, que aporta una informaci�n clara hacia adelante y hacia atr�s acerca de las mismas, a modo de instrucciones de trabajo comprensibles',1);
INSERT INTO indicators VALUES(51,'AUTISM','Las personas con TEA tienen asignadas responsabilidades y participan activamente en la Organizaci�n',1);
INSERT INTO evidences VALUES(1,51,'AUTISM','Cada persona con TEA tiene asignadas responsabilidades en la Organizaci�n, adecuadas a sus capacidades e intereses',1);
INSERT INTO evidences VALUES(2,51,'AUTISM','Existe una revisi�n peri�dica que eval�a el grado de adecuaci�n y desarrollo de las responsabilidades asignadas y facilita el reconocimiento hacia las personas con TEA',1);
INSERT INTO evidences VALUES(3,51,'AUTISM','Existen foros, instrumentos, para recoger aportaciones y sugerencias de las personas con TEA',1);
INSERT INTO evidences VALUES(4,51,'AUTISM','Las personas con TEA participan, siempre que sea posible, en el proceso de dise�o, creaci�n y adaptaci�n de materiales y actividades',1);
INSERT INTO indicators VALUES(52,'AUTISM','Se dispone de apoyo y seguimiento t�cnico integrado en el equipo profesional',2);
INSERT INTO evidences VALUES(1,52,'AUTISM','Se dispone de personal t�cnico especializado en las diferentes �reas de intervenci�n',1);
INSERT INTO evidences VALUES(2,52,'AUTISM','El trabajo t�cnico est� integrado dentro del equipo, de forma que se planifican y se abordan en grupo las estrategias de intervenci�n individualizadas',1);
INSERT INTO evidences VALUES(3,52,'AUTISM','Existe un sistema de evaluaci�n continua de los programas espec�ficos de intervenci�n',1);
INSERT INTO evidences VALUES(4,52,'AUTISM','El seguimiento t�cnico detecta regularmente nuevas necesidades de intervenci�n o apoyos espec�ficos',1);
INSERT INTO indicators VALUES(53,'AUTISM','El horario y ritmo de trabajo de las personas con TEA se adapta a sus necesidades',3);
INSERT INTO evidences VALUES(1,53,'AUTISM','Existen horarios estables y personalizados',1);
INSERT INTO evidences VALUES(2,53,'AUTISM','Se informa anticipadamente a cada persona con TEA del horario que tiene, as� como de los posibles cambios e imprevistos',1);
INSERT INTO evidences VALUES(3,53,'AUTISM','Se contemplan tiempos en los que cada persona puede desarrollar actividades de libre elecci�n',1);
INSERT INTO evidences VALUES(4,53,'AUTISM','Existe un ajuste entre tiempo y ritmo de trabajo, y tiempo de descanso',1);
INSERT INTO indicators VALUES(54,'AUTISM','El horario y distribuci�n de tiempos de los profesionales se adecua a las necesidades de las personas con TEA',3);
INSERT INTO evidences VALUES(1,54,'AUTISM','Existe un horario estable y predecible',1);
INSERT INTO evidences VALUES(2,54,'AUTISM','El horario y distribuci�n de tiempos garantiza una organizaci�n de los tiempos de tr�nsito entre actividades, de descanso, de entradas y salidas',1);
INSERT INTO evidences VALUES(3,54,'AUTISM','Ante cambios e imprevistos existe un sistema de reorganizaci�n que no afecta a las personas con TEA',1);
INSERT INTO evidences VALUES(4,54,'AUTISM','Se rentabilizan los recursos personales asignando a los profesionales tareas y funciones, que inciden en la mejora de la calidad del servicio, en los momentos en que no sea necesaria o no tengan asignada atenci�n directa',1);
INSERT INTO indicators VALUES(55,'AUTISM','Se facilita la comunicaci�n entre todas las personas vinculadas a la Organizaci�n',2);
INSERT INTO evidences VALUES(1,55,'AUTISM','Existen v�as de comunicaci�n formal entre personas vinculadas a la Organizaci�n seg�n el �mbito, los implicados y el tema',1);
INSERT INTO evidences VALUES(2,55,'AUTISM','Es posible el acceso de todos los profesionales de la Organizaci�n a los diferentes canales de comunicaci�n',1);
INSERT INTO evidences VALUES(3,55,'AUTISM','Se utilizan diferentes sistemas, v�as y apoyos para promover la comunicaci�n de las personas con TEA',1);
INSERT INTO evidences VALUES(4,55,'AUTISM','Existen canales que posibilitan una comunicaci�n continua y r�pida entre programas y servicios, con la familia o tutores, y otras personas relacionadas con la persona con TEA',1);
INSERT INTO indicators VALUES(56,'AUTISM','Se planifican y promueven tiempos y espacios para la coordinaci�n',2);
INSERT INTO evidences VALUES(1,56,'AUTISM','Existen tiempos programados para reuniones y coordinaci�n',1);
INSERT INTO evidences VALUES(2,56,'AUTISM','Existe una organizaci�n y una din�mica que facilita la eficacia de las reuniones: orden del d�a previo, coordinador, acta, distribuci�n de tiempos por temas',1);
INSERT INTO evidences VALUES(3,56,'AUTISM','Existe la posibilidad de participar activamente en las reuniones e incorporar temas por parte de todos los participantes',1);
INSERT INTO evidences VALUES(4,56,'AUTISM','Se realiza un seguimiento de la eficacia de las conclusiones y decisiones que se toman en las reuniones',1);
INSERT INTO indicators VALUES(57,'AUTISM','Existe coordinaci�n con otros programas y servicios relacionados con la persona con TEA',2);
INSERT INTO evidences VALUES(1,57,'AUTISM','Existen v�as de coordinaci�n entre los diferentes servicios de la Organizaci�n',1);
INSERT INTO evidences VALUES(2,57,'AUTISM','Existen v�as de coordinaci�n con otros servicios externos a la Organizaci�n, pero relacionados con los programas que se promueven desde �sta',1);
INSERT INTO evidences VALUES(3,57,'AUTISM','Existe un proceso que garantiza la gesti�n y difusi�n de la informaci�n, y el conocimiento a todas las personas, servicios o entidades relacionados con la persona con TEA',1);
INSERT INTO evidences VALUES(4,57,'AUTISM','Existe un registro de informaci�n e incidencias de cada servicio que centralice la informaci�n y ayude a coordinarse con el resto de servicios',1);
INSERT INTO indicators VALUES(58,'AUTISM','Se facilita la comunicaci�n a las personas con TEA',4);
INSERT INTO evidences VALUES(1,58,'AUTISM','Cada persona con TEA tiene definido qu� sistema de comunicaci�n utiliza, adaptado a sus caracter�sticas � perfil individual',1);
INSERT INTO evidences VALUES(2,58,'AUTISM','Todos los profesionales, familias y personas que forman parte de la vida de la persona con TEA, tienen acceso al conocimiento y uso de los sistemas de comunicaci�n que �sta utiliza',1);
INSERT INTO evidences VALUES(3,58,'AUTISM','Se utilizan los sistemas de comunicaci�n con diferentes objetivos: anticipar, facilitar peticiones y deseos, informar, elegir, rechazar',1);
INSERT INTO evidences VALUES(4,58,'AUTISM','Existe coherencia entre los soportes de estructuraci�n y los de comunicaci�n que se utilizan con cada persona',1);
INSERT INTO indicators VALUES(59,'AUTISM','Se realiza una evaluaci�n interna de la Organizaci�n',2);
INSERT INTO evidences VALUES(1,59,'AUTISM','Existen instrumentos para evaluar internamente, de forma peri�dica, la Organizaci�n',1);
INSERT INTO evidences VALUES(2,59,'AUTISM','En la evaluaci�n participan todos los profesionales de la Organizaci�n, las familias y, en los casos en que sea posible, las personas con TEA, y sus resultados se difunden a todos ellos',1);
INSERT INTO evidences VALUES(3,59,'AUTISM','Existe una v�a para registrar de forma inmediata puntos d�biles o situaciones susceptibles de mejora en la Organizaci�n',1);
INSERT INTO evidences VALUES(4,59,'AUTISM','Se analiza e implantan acciones de mejora tras la detecci�n de puntos d�biles en la organizaci�n.',1);
INSERT INTO indicators VALUES(60,'AUTISM','La mejora de la Organizaci�n contempla una evaluaci�n externa',1);
INSERT INTO evidences VALUES(1,60,'AUTISM','En la Organizaci�n se realizan evaluaciones externas de forma peri�dica',1);
INSERT INTO evidences VALUES(2,60,'AUTISM','En la evaluaci�n est� implicada toda la Organizaci�n: profesionales, familias y, en los casos en que sea posible, las personas con TEA',1);
INSERT INTO evidences VALUES(3,60,'AUTISM','Los resultados de las evaluaciones se difunden a todas las personas implicadas',1);
INSERT INTO evidences VALUES(4,60,'AUTISM','De la evaluaci�n externa se derivan mejoras en la Organizaci�n',1);
INSERT INTO indicators VALUES(61,'AUTISM','La direcci�n de la Organizaci�n impulsa la mejora continua',3);
INSERT INTO evidences VALUES(1,61,'AUTISM','La Organizaci�n cuenta con un Plan Estrat�gico que contempla la elaboraci�n de Planes Anuales de Mejora',1);
INSERT INTO evidences VALUES(2,61,'AUTISM','La direcci�n motiva e involucra a los profesionales para que propongan y desarrollen acciones de mejora',1);
INSERT INTO evidences VALUES(3,61,'AUTISM','La direcci�n est� implicada activamente en las acciones de mejora propuestas',1);
INSERT INTO evidences VALUES(4,61,'AUTISM','La direcci�n reconoce los esfuerzos y logros de mejora realizados por las personas que la integran',1);
INSERT INTO indicators VALUES(62,'AUTISM','Se rentabilizan los recursos humanos',2);
INSERT INTO evidences VALUES(1,62,'AUTISM','El n�mero de profesionales es suficiente y se adecua a las necesidades de ratio de cada persona con TEA y/o actividad',1);
INSERT INTO evidences VALUES(2,62,'AUTISM','Existe una asignaci�n clara de funciones a los profesionales',1);
INSERT INTO evidences VALUES(3,62,'AUTISM','Existen profesionales especializados en las diferentes �reas',1);
INSERT INTO evidences VALUES(4,62,'AUTISM','Existe una organizaci�n del personal no contratado: voluntarios, alumnos en pr�cticas..., con asignaci�n de tiempos y tareas a realizar, y que cumple con la normativa que regula sus derechos y deberes',1);
INSERT INTO indicators VALUES(63,'AUTISM','Existe una adecuada organizaci�n del trabajo de los profesionales',3);
INSERT INTO evidences VALUES(1,63,'AUTISM','Existe una estructuraci�n clara de los tiempos, actividades y agrupamientos asignados a cada profesional a lo largo de toda la jornada',1);
INSERT INTO evidences VALUES(2,63,'AUTISM','Existen tiempos de intervenci�n dedicados a desarrollar programas espec�ficos, individualizados y con profesionales especializados',1);
INSERT INTO evidences VALUES(3,63,'AUTISM','Existe posibilidad de compatibilizar distintas situaciones de trabajo: individual, grupal..., seg�n las necesidades de las personas con TEA y del momento',1);
INSERT INTO evidences VALUES(4,63,'AUTISM','Existe una definici�n y asignaci�n de responsabilidades y funciones dentro de la Organizaci�n que implica a todos los profesionales',1);
INSERT INTO indicators VALUES(64,'AUTISM','Se rentabilizan los recursos materiales',1);
INSERT INTO evidences VALUES(1,64,'AUTISM','Los recursos est�n a disposici�n de todos los profesionales',1);
INSERT INTO evidences VALUES(2,64,'AUTISM','Se incorporan o generan nuevos materiales de apoyo a la intervenci�n, seg�n surgen las necesidades de las personas con TEA',1);
INSERT INTO evidences VALUES(3,64,'AUTISM','Se utilizan ayudas t�cnicas y sistemas de seguridad en las m�quinas para facilitar el acceso del mayor n�mero posible de personas con TEA a las mismas',1);
INSERT INTO evidences VALUES(4,64,'AUTISM','Los recursos est�n puestos al d�a, en buen estado, son funcionales, y apropiados a la edad y a las necesidades ergon�micas de cada persona con TEA',1);
INSERT INTO indicators VALUES(65,'AUTISM','El entorno f�sico favorece la participaci�n, accesibilidad y la autonom�a de las personas con TEA',3);
INSERT INTO evidences VALUES(1,65,'AUTISM','Existen suficientes espacios y se adecuan de forma flexible a las necesidades puntuales y cambiantes que pueden tener las personas con TEA',1);
INSERT INTO evidences VALUES(2,65,'AUTISM','La informaci�n / estructuraci�n espacial facilita la comprensi�n y el desenvolvimiento de la forma m�s aut�noma y segura posible de las personas con TEA',1);
INSERT INTO evidences VALUES(3,65,'AUTISM','Se minimizan las barreras arquitect�nicas o dificultades de acceso para las personas con TEA',1);
INSERT INTO evidences VALUES(4,65,'AUTISM','Los espacios se adecuan a la normativa que regula la construcci�n y favorecen aspectos como la salud y la higiene',1);
INSERT INTO indicators VALUES(66,'AUTISM','Existen alianzas de colaboraci�n con otras entidades, pertenezcan o no al sector de la discapacidad',1);
INSERT INTO evidences VALUES(1,66,'AUTISM','Existen v�nculos / convenios con otros recursos y/o entidades relacionadas con los objetivos del servicio',1);
INSERT INTO evidences VALUES(2,66,'AUTISM','Existen v�nculos / convenios con servicios de salud, trabajo y bienestar social',1);
INSERT INTO evidences VALUES(3,66,'AUTISM','Existen v�nculos / convenios con otros recursos de la comunidad en general: empresas, deportes, ocio',1);
INSERT INTO evidences VALUES(4,66,'AUTISM','Existe un programa de captaci�n, formaci�n y seguimiento del voluntariado',1);
INSERT INTO indicators VALUES(67,'AUTISM','La Organizaci�n asume un compromiso de responsabilidad social',2);
INSERT INTO evidences VALUES(1,67,'AUTISM','La Organizaci�n contrata servicios o adquiere productos necesarios, realizando una discriminaci�n positiva hacia entidades o empresas que integren personas en riesgo de exclusi�n social',1);
INSERT INTO evidences VALUES(2,67,'AUTISM','La Organizaci�n garantiza la calidad de los productos o servicios contratados a personas o empresas ajenas, y las condiciones en que estos han sido realizados',1);
INSERT INTO evidences VALUES(3,67,'AUTISM','La Organizaci�n cuenta con procesos de respeto al medio ambiente: gesti�n de residuos, reciclado de productos',1);
INSERT INTO evidences VALUES(4,67,'AUTISM','Los espacios carecen de las barreras arquitect�nicas estipuladas por la normativa, permitiendo el acceso y la autonom�a a personas con distintas discapacidades',1);
INSERT INTO indicators VALUES(68,'AUTISM','Se favorece la sensibilizaci�n y la imagen social positiva',2);
INSERT INTO evidences VALUES(1,68,'AUTISM','Se organizan actividades dirigidas y abiertas a toda la comunidad (exposiciones, jornadas, conferencias)',1);
INSERT INTO evidences VALUES(2,68,'AUTISM','Se participa en proyectos, iniciativas promovidas desde los diferentes recursos de la comunidad, cuyo objetivo prioritario es la presencia y sensibilizaci�n social',1);
INSERT INTO evidences VALUES(3,68,'AUTISM','Existen un plan o proceso para dar respuesta a las demandas externas de informaci�n, visitas, intercambios, formaci�n',1);
INSERT INTO evidences VALUES(4,68,'AUTISM','Existe un Plan de Comunicaci�n Externa para divulgar y ofrecer informaci�n sobre autismo: folletos de divulgaci�n, p�gina Web, publicaciones, videos, aparici�n en medios de comunicaci�n',1);

INSERT INTO users VALUES('pahita@ubu.es','ADMIN','123456','Pablo','Ah�ta del Barrio',622817551);
--CREATE LOGIN [pahita@ubu.es] WITH PASSWORD = '123456';
INSERT INTO users VALUES('jlcgomez@ubu.es','ORGANIZATION','123456','Jose Luis','Cuesta G�mez',-1);
--CREATE LOGIN [jlcgomez@ubu.es] WITH PASSWORD = '123456';
INSERT INTO users VALUES('mgg@ubu.es','ORGANIZATION','123456','Miguel','G�mez Gentil',-1);
--CREATE LOGIN [mgg@ubu.es] WITH PASSWORD = '123456';

INSERT INTO administrators VALUES('pahita@ubu.es','ADMIN');
INSERT INTO addresses VALUES(1,'Calle','las Rebolledas',-1,-1,'-',09001,'Burgos','Burgos','Castilla y Le�n','Spain');
INSERT INTO organizations VALUES(1,'EVALUATOR','AUTISM','Fundaci�n Miradas',1,'fmiradas@fundacionmiradas.org',947072019,'Fundaci�n Miradas es una organizaci�n de �mbito estatal creada en 2013 por la Asociaci�n Autismo Burgos que orienta sus actuaciones a la prestaci�n de apoyos a la capacidad jur�dica y a la defensa de los derechos de las personas con autismo y a sus familias, as� como a la mejora de la calidad de vida de estas. De igual forma, la Fundaci�n desarrolla diferentes iniciativas que fomenten la sensibilizaci�n, el conocimiento, el estudio y la investigaci�n sobre las personas con un TEA y sus familias. En 2015 junto con la Universidad de Burgos, crea la C�tedra Miradas por el Autismo (y enlazas tanto a la UBU y a la C�tedra), la primera que centra su atenci�n en el colectivo de las personas con TEA y sus familias en Espa�a.');
--INSERT INTO centers VALUES(
INSERT INTO evaluatorOrganizations VALUES(1,'EVALUATOR','AUTISM');
INSERT INTO autisticEvaluatorOrganizations VALUES(1,'AUTISM');

INSERT INTO organizationUsers VALUES('jlcgomez@ubu.es','ORGANIZATION',1,'EVALUATOR','AUTISM');
INSERT INTO organizationUsers VALUES('mgg@ubu.es','ORGANIZATION',1,'EVALUATOR','AUTISM');
INSERT INTO evaluatorOrganizationUsers VALUES('jlcgomez@ubu.es',1,'EVALUATOR','AUTISM');
INSERT INTO evaluatorOrganizationUsers VALUES('mgg@ubu.es',1,'EVALUATOR','AUTISM');
INSERT INTO autisticEvaluatorOrgUsers VALUES('jlcgomez@ubu.es',1,'AUTISM');
INSERT INTO autisticEvaluatorOrgUsers VALUES('mgg@ubu.es',1,'AUTISM');


select * from users;