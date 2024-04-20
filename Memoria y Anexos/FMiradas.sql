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

INSERT INTO indicators VALUES(1,'AUTISM','Existen programas de atención sanitaria individualizados y actualizados',3);
INSERT INTO evidences VALUES(1,1,'AUTISM','Se dispone de un expediente médico individual, confidencial y actualizado, que contiene información referida a: medicación, enfermedades, alergias, pruebas realizadas, historial',1);
INSERT INTO evidences VALUES(2,1,'AUTISM','Se realizan revisiones de seguimiento y prevención periódicas, al menos una vez al año',1);
INSERT INTO evidences VALUES(3,1,'AUTISM','En los casos necesarios se desarrollan acciones de sensibilización, adaptación al entorno sanitario y a las diferentes pruebas médicas',1);
INSERT INTO evidences VALUES(4,1,'AUTISM','Existen profesionales médicos de referencia, conocedores de las características del autismo, de distintas especialidades: medicina general, psiquiatría, odontología',1);
INSERT INTO indicators VALUES(2,'AUTISM','Se garantiza la correcta administración y seguimiento de los tratamientos farmacológicos',4);
INSERT INTO evidences VALUES(1,2,'AUTISM','Se realizan análisis periódicos de control y seguimiento de las medicaciones',1);
INSERT INTO evidences VALUES(2,2,'AUTISM','Existen registros que garantizan la correcta administración de la medicación y reflejan posibles incidencias',1);
INSERT INTO evidences VALUES(3,2,'AUTISM','Se registran, analizan y se informa a la familia y a los médicos que lo han prescrito, de los posibles efectos secundarios derivados de los cambios de medicación',1);
INSERT INTO evidences VALUES(4,2,'AUTISM','Existe un protocolo-proceso de la administración (responsable, control, autorizaciones ) conocido por todos',1);
INSERT INTO indicators VALUES(3,'AUTISM','Se contempla la intervención individualizada en el ámbito del cuidado y autonomía personal',1);
INSERT INTO evidences VALUES(1,3,'AUTISM','Cada plan de desarrollo individual contiene objetivos referidos al cuidado y autonomía personal',1);
INSERT INTO evidences VALUES(2,3,'AUTISM','Existen momentos específicos de intervención en habilidades funcionales, referidas al cuidado y autonomía personal, aprovechando contextos naturales',1);
INSERT INTO evidences VALUES(3,3,'AUTISM','Los profesionales que trabajan en contacto directo con las personas con TEA conocen y coordinan las pautas a seguir para procurar el bienestar físico de cada una de ellas, a través de la promoción de habilidades referidas a la autonomía personal: vestido, higiene, comida, autonomía personal',1);
INSERT INTO evidences VALUES(4,3,'AUTISM','Las instalaciones cuentan con espacios suficientes y adecuados para la realización de actividades referidas al cuidado y la autonomía personal: vestuarios, aseos, espacios que favorezcan la privacidad',1);
INSERT INTO indicators VALUES(4,'AUTISM','Se desarrollan actuaciones referidas a la seguridad e higiene en los diferentes ámbitos',1);
INSERT INTO evidences VALUES(1,4,'AUTISM','Existe un análisis previo, de forma individualizada, de diferentes situaciones, materiales o actividades que puedan implicar riesgo',1);
INSERT INTO evidences VALUES(2,4,'AUTISM','Existen ayudas técnicas y dispositivos de seguridad que, sin crear un entorno restrictivo, favorecen la autonomía y la seguridad de las personas, minimizando los riesgos que pueden provenir del contexto',1);
INSERT INTO evidences VALUES(3,4,'AUTISM','Las instalaciones facilitan el desenvolvimiento autónomo: seguridad, ausencia de barreras, estructuración de espacios',1);
INSERT INTO evidences VALUES(4,4,'AUTISM','Se dispone de protocolos de formación e intervención que permiten prevenir y abordar situaciones de emergencia de forma eficaz (planes de evacuación, primeros auxilios)',1);
INSERT INTO indicators VALUES(5,'AUTISM','Se contemplan medidas preventivas individualizadas para mantener una salud adecuada',1);
INSERT INTO evidences VALUES(1,5,'AUTISM','Las condiciones físicas del entorno (mobiliario, actividades, puestos de trabajo) se adaptan a las características y necesidades ergonómicas de cada persona con TEA, promoviendo su bienestar físico: adecuada luz y temperatura, control postural, ruido ambiental',1);
INSERT INTO evidences VALUES(2,5,'AUTISM','Se promueve una nutrición adecuada (menús adaptados, dietas, adecuación a las posibilidades de deglución de cada persona)',1);
INSERT INTO evidences VALUES(3,5,'AUTISM','Cada persona participa en programas dirigidos a mantener una buena salud y prevenir un posible deterioro físico (control de peso, ejercicio físico, deporte, fisioterapia), con profesionales específicos',1);
INSERT INTO evidences VALUES(4,5,'AUTISM','Se realizan programas de formación para promover hábitos de salud, referidos a habilidades de autonomía, prevención, sexualidad',1);
INSERT INTO indicators VALUES(6,'AUTISM','El ambiente promueve un estado emocional positivo en las Personas con TEA',4);
INSERT INTO evidences VALUES(1,6,'AUTISM','Se utilizan sistemas de estructuración claros, sencillos y adaptados para que el ambiente sea predecible',1);
INSERT INTO evidences VALUES(2,6,'AUTISM','Existe un tratamiento individualizado de los problemas emocionales',1);
INSERT INTO evidences VALUES(3,6,'AUTISM','Cualquier nueva intervención o tratamiento por parte de los profesionales, se pone en práctica tras obtener el consentimiento informado de la persona con TEA o en su caso de las personas que la representen (familia, tutores)',1);
INSERT INTO evidences VALUES(4,6,'AUTISM','Se conocen y tienen en cuenta las afinidades personales y los gustos, para configurar grupos de compañeros y planificar actividades',1);
INSERT INTO indicators VALUES(7,'AUTISM','Se promueve la máxima estabilidad emocional en la vida de las personas con TEA',2);
INSERT INTO evidences VALUES(1,7,'AUTISM','Existe una estructura flexible de funcionamiento que permite resolver de forma inmediata los imprevistos que afectan a la estabilidad en la organización: ausencia de un profesional, cambios en las actividades previstas, alteración de espacios',1);
INSERT INTO evidences VALUES(2,7,'AUTISM','Se minimizan las condiciones de estrés que pueden afectar a la persona (informando por anticipado de los cambios de profesionales, horario y actividades)',1);
INSERT INTO evidences VALUES(3,7,'AUTISM','Se utilizan sistemas de estructuración que facilitan la orientación y el uso de los distintos espacios',1);
INSERT INTO evidences VALUES(4,7,'AUTISM','La persona con TEA tiene personas de referencia claras en su vida (familiares, profesionales, compañeros, amigos), y se contempla la intervención ante situaciones de ausencia (duelo, cambios de personal)',1);
INSERT INTO indicators VALUES(8,'AUTISM','Se desarrollan programas individualizados de intervención conductual positiva',3);
INSERT INTO evidences VALUES(1,8,'AUTISM','Existen unas pautas generales de prevención de conductas problemáticas',1);
INSERT INTO evidences VALUES(2,8,'AUTISM','Se registran las conductas problemáticas y se realiza un análisis funcional y contextual, previo al diseño de estrategias de intervención',1);
INSERT INTO evidences VALUES(3,8,'AUTISM','Se desarrollan estrategias de prevención e intervención ante conductas problemáticas (resolución de problemas, habilidades comunicativas, intervención física, estrategias de autocontrol) siempre basadas en parámetros éticos y respetuosos con la persona',1);
INSERT INTO evidences VALUES(4,8,'AUTISM','Existe un protocolo de “intervención física” que asegura la intervención adecuada en situaciones de conductas problemáticas, con el objetivo de asegurar la salud y seguridad de la persona',1);
INSERT INTO indicators VALUES(9,'AUTISM','La persona con TEA y/o su representante participa en la planificación, ejecución y evaluación de su plan de desarrollo individual',2);
INSERT INTO evidences VALUES(1,9,'AUTISM','Existen canales de participación y expresión de las personas con TEA y/o sus representantes, en relación con el Plan de Desarrollo Individual',1);
INSERT INTO evidences VALUES(2,9,'AUTISM','Se proporcionan apoyos para que las personas puedan llevar a cabo elecciones y decisiones',1);
INSERT INTO evidences VALUES(3,9,'AUTISM','Se analizan y tienen en cuenta los deseos, preferencias e intereses de las personas',1);
INSERT INTO evidences VALUES(4,9,'AUTISM','Las actividades se adaptan y estructuran de forma que se garantiza el éxito en su realización de la forma más autónoma posible',1);
INSERT INTO indicators VALUES(10,'AUTISM','Las personas con TEA cuentan con apoyos personales individualizados',3);
INSERT INTO evidences VALUES(1,10,'AUTISM','Cada persona con TEA tiene un profesional, con afinidad, que le sirve de referencia y que realiza un seguimiento mas exhaustivo y cercano',1);
INSERT INTO evidences VALUES(2,10,'AUTISM','Cada persona tiene un grupo de compañeros de referencia, aunque no esté en todo momento junto a ellos',1);
INSERT INTO evidences VALUES(3,10,'AUTISM','Las personas cuentan con el personal necesario en las actividades realizadas en el entorno de la comunidad',1);
INSERT INTO evidences VALUES(4,10,'AUTISM','Se promueve y apoya a las personas del entorno para que se impliquen como apoyos naturales de las personas con TEA',1);
INSERT INTO indicators VALUES(11,'AUTISM','Se respeta la intimidad',3);
INSERT INTO evidences VALUES(1,11,'AUTISM','Existen espacios, tiempos y pertenencias personalizadas',1);
INSERT INTO evidences VALUES(2,11,'AUTISM','Se respeta, o negocia en su caso, la decisión de la persona de utilizar su propio espacio, sus pertenencias y/o emplear libremente su tiempo cuando lo requiera',1);
INSERT INTO evidences VALUES(3,11,'AUTISM','Se favorece la intimidad en la realización de actividades referidas al aseo, vestido, cuidado personal',1);
INSERT INTO evidences VALUES(4,11,'AUTISM','El uso de la imagen e información sobre las personas con TEA está sujeto a un protocolo de utilización y a la normativa sobre protección de datos que garantiza el respeto y la confidencialidad',1);
INSERT INTO indicators VALUES(12,'AUTISM','Se promueven y respetan las pertenencias',2);
INSERT INTO evidences VALUES(1,12,'AUTISM','Los espacios y pertenencias personales son estables, están cuidados, y son adecuados a la edad cronológica, a las necesidades y preferencias de la persona',1);
INSERT INTO evidences VALUES(2,12,'AUTISM','Existe un protocolo que regula y garantiza la obtención de refuerzos individualizados y pertenencias de libre elección',1);
INSERT INTO evidences VALUES(3,12,'AUTISM','Cada persona recibe una contraprestación real por su trabajo productivo',1);
INSERT INTO evidences VALUES(4,12,'AUTISM','Se promueven habilidades relacionadas con la gestión del dinero y pertenencias personales',1);
INSERT INTO indicators VALUES(13,'AUTISM','Se promueven las relaciones sociales significativas',3);
INSERT INTO evidences VALUES(1,13,'AUTISM','Se analizan y respetan las preferencias e incompatibilidades entre las personas con TEA para planificar y potenciar los grupos de compañeros',1);
INSERT INTO evidences VALUES(2,13,'AUTISM','Existe un programa individualizado de inclusión social y laboral, que fomenta la interacción con compañeros y personas sin discapacidad en los diferentes entornos',1);
INSERT INTO evidences VALUES(3,13,'AUTISM','Existe un programa que favorece las relaciones sociales: habilidades sociales y comunicativas, expresión de emociones, afectividad y sexualidad',1);
INSERT INTO evidences VALUES(4,13,'AUTISM','Se promueve y apoya a las personas del entorno para que se impliquen como apoyos naturales de las personas con TEA',1);
INSERT INTO indicators VALUES(14,'AUTISM','Se promueve el desarrollo de las capacidades e intereses individuales',4);
INSERT INTO evidences VALUES(1,14,'AUTISM','Se realiza una evaluación de las habilidades, intereses y preferencias individuales de cada persona con TEA en los distintos ámbitos de desarrollo',1);
INSERT INTO evidences VALUES(2,14,'AUTISM','Los planes de desarrollo individualizados contemplan los intereses, las necesidades, los gustos y preferencias de la persona en todas las áreas de desarrollo, y en los distintos ámbitos y momentos',1);
INSERT INTO evidences VALUES(3,14,'AUTISM','Las actividades que se desarrollan en los distintos programas o servicios, se diseñan o seleccionan de forma que, además de dar respuesta a las necesidades, intereses y capacidades personales, respondan a un criterio de funcionalidad',1);
INSERT INTO evidences VALUES(4,14,'AUTISM','En el ámbito de la inclusión social, cada persona tiene diseñado un itinerario personalizado, planificado teniendo en cuenta sus capacidades e intereses, que abarca todos los pasos del proceso: evaluación funcional, búsqueda de recursos, formación, adaptación…',1);
INSERT INTO indicators VALUES(15,'AUTISM','Se promueve el avance y el desarrollo continuo de la persona',3);
INSERT INTO evidences VALUES(1,15,'AUTISM','Se utilizan adaptaciones técnicas, apoyos visuales... en función de las necesidades de cada persona',1);
INSERT INTO evidences VALUES(2,15,'AUTISM','Se diseñan materiales, programas y actividades acordes con un criterio evolutivo y de funcionalidad',1);
INSERT INTO evidences VALUES(3,15,'AUTISM','Existe un sistema de evaluación de habilidades sociales, laborales,... que permite contrastar el avance a lo largo del tiempo',1);
INSERT INTO evidences VALUES(4,15,'AUTISM','Se planifica y promueve la retirada gradual de apoyos',1);
INSERT INTO indicators VALUES(16,'AUTISM','Se garantiza el respeto a la identidad y dignidad de la persona',4);
INSERT INTO evidences VALUES(1,16,'AUTISM','Existen unas normas de organización y funcionamiento interno en las que se recogen derechos y deberes referidos a profesionales, familias, organización y personas con TEA, que tienen como referencia la Declaración Universal de los Derechos Humanos y, de forma especial, la Carta de los Derechos de las Personas con Autismo',1);
INSERT INTO evidences VALUES(2,16,'AUTISM','Existe un código de ética profesional, consensuado entre familias y profesionales, que define como deben ser las relaciones, las actuaciones y actitudes hacia las personas con TEA',1);
INSERT INTO evidences VALUES(3,16,'AUTISM','La Organización defiende los derechos de las personas con TEA',1);
INSERT INTO evidences VALUES(4,16,'AUTISM','La Organización no es restrictiva, fomenta nuevas oportunidades, no coarta las posibilidades de elección ni de desarrollo de las personas',1);
INSERT INTO indicators VALUES(17,'AUTISM','Se garantiza la integridad física',4);
INSERT INTO evidences VALUES(1,17,'AUTISM','No se utiliza ningún tipo de restricción física ni tratamiento farmacológico si no está previamente consensuado con la familia, un técnico cualificado y, en los casos en que sea posible, con la propia persona con TEA, y en caso de fármacos éstos deben haber sido siempre prescritos por los médicos',1);
INSERT INTO evidences VALUES(2,17,'AUTISM','Se analizan, por anticipado, los posibles riesgos derivados de la programación de una nueva actividad',1);
INSERT INTO evidences VALUES(3,17,'AUTISM','Existen pautas y medidas para controlar posibles abusos físicos y/o emocionales',1);
INSERT INTO evidences VALUES(4,17,'AUTISM','Se utilizan adaptaciones técnicas y dispositivos de seguridad que minimizan los riesgos de daños físicos referidos a la utilización y el uso de espacios, herramientas, máquinas y materiales',1);
INSERT INTO indicators VALUES(18,'AUTISM','Las personas reciben formación variada y adaptada, previa a la emisión de conductas de autodeterminación',1);
INSERT INTO evidences VALUES(1,18,'AUTISM','Se apoya el que la persona comprenda y planifique la secuencia de pasos de las actividades que realiza',1);
INSERT INTO evidences VALUES(2,18,'AUTISM','Se utilizan apoyos para favorecer el acceso de las personas con TEA a la información y toma de decisiones (pictogramas...)',1);
INSERT INTO evidences VALUES(3,18,'AUTISM','Se planifican actividades que permiten poner en práctica las habilidades adquiridas  relacionadas con la conducta de autodeterminación',1);
INSERT INTO evidences VALUES(4,18,'AUTISM','Se revisan y actualizan periódicamente las actividades, apoyos y materiales planificados, para poder readecuarlos a las necesidades cambiantes de las personas con TEA, o se estudian nuevas alternativas',1);
INSERT INTO indicators VALUES(19,'AUTISM','Las personas participan en la planificación y ejercen un control sobre su vida',4);
INSERT INTO evidences VALUES(1,19,'AUTISM','Se anticipan y planifican las actividades a realizar, disponiendo toda persona de momentos en los que puede elegir libremente qué hacer o no hacer en su tiempo libre',1);
INSERT INTO evidences VALUES(2,19,'AUTISM','La persona tiene oportunidades, y dispone de un sistema de comunicación adaptado, para comunicar necesidades, emociones, y para realizar elecciones',1);
INSERT INTO evidences VALUES(3,19,'AUTISM','Se promueven actividades encaminadas a desarrollar capacidades de planificación que permitan a la persona elegir o participar en las decisiones que afectan a su vida, tanto en lo referido a cuestiones cotidianas como a cuestiones de mayor trascendencia para su futuro',1);
INSERT INTO evidences VALUES(4,19,'AUTISM','Se aprovechan o provocan situaciones controladas o riesgos asumibles (posibles imprevistos, situaciones en las que es necesario pedir ayuda...), que impliquen habilidades de resolución de problemas en distintos contextos cotidianos, se trabajan estrategias y se ofrecen apoyos a la persona para su resolución.',1);
INSERT INTO indicators VALUES(20,'AUTISM','Se promueve la inclusión social de las personas con TEA',4);
INSERT INTO evidences VALUES(1,20,'AUTISM','Se realiza un análisis ecológico y funcional previo a la inclusión social de la persona',1);
INSERT INTO evidences VALUES(2,20,'AUTISM','La persona participa en actividades y programas realizados en distintos entornos comunitarios',1);
INSERT INTO evidences VALUES(3,20,'AUTISM','Se facilita y apoya la inclusión en recursos y actividades comunitarias',1);
INSERT INTO evidences VALUES(4,20,'AUTISM','Se utilizan los medios de comunicación convencionales, para la información y divulgación hacia la sociedad',1);
INSERT INTO indicators VALUES(21,'AUTISM','Las actuaciones con la persona con TEA tienen en cuenta las expectativas de la familia',1);
INSERT INTO evidences VALUES(1,21,'AUTISM','Existen procedimientos para recoger y revisar periódicamente las expectativas de la familia hacia la persona con TEA y hacia la Organización',1);
INSERT INTO evidences VALUES(2,21,'AUTISM','El Plan de Desarrollo Personal integra las expectativas de la familia que se ajustan a las necesidades y capacidades de las personas con TEA',1);
INSERT INTO evidences VALUES(3,21,'AUTISM','La Organización promueve el que la familia se integre activamente en la red de apoyos de la persona con TEA',1);
INSERT INTO evidences VALUES(4,21,'AUTISM','Los objetivos del Plan de Desarrollo Personal respetan el estilo de vida y de relación familiar de la persona con TEA',1);
INSERT INTO indicators VALUES(22,'AUTISM','Se facilita la implicación de las familias en la Organización',1);
INSERT INTO evidences VALUES(1,22,'AUTISM','Las familias participan en la elaboración de los Planes de Desarrollo Personal de la persona con TEA, y pueden tener información de su evolución en cualquier momento',1);
INSERT INTO evidences VALUES(2,22,'AUTISM','Existe un plan de información, formación y asesoramiento a las familias, con profesionales responsables que las conocen',1);
INSERT INTO evidences VALUES(3,22,'AUTISM','Existe una variedad de vías de implicación y participación en la Organización',1);
INSERT INTO evidences VALUES(4,22,'AUTISM','Existen diferentes vías de comunicación / coordinación con los servicios y profesionales',1);
INSERT INTO indicators VALUES(23,'AUTISM','Se favorece un aumento del nivel de satisfacción en las familias',1);
INSERT INTO evidences VALUES(1,23,'AUTISM','Existen vías para medir y analizar el nivel de satisfacción: encuestas, entrevistas personales',1);
INSERT INTO evidences VALUES(2,23,'AUTISM','Existen vías para comunicar incidencias, realizar sugerencias y propuestas de mejora',1);
INSERT INTO evidences VALUES(3,23,'AUTISM','Se analizan y tienen en cuenta las incidencias y propuestas formuladas por las familias',1);
INSERT INTO evidences VALUES(4,23,'AUTISM','Se implica a las familias en los procesos de mejora',1);
INSERT INTO indicators VALUES(24,'AUTISM','Se conocen, valoran y se tienen en cuenta las propuestas e iniciativas provenientes de los profesionales',1);
INSERT INTO evidences VALUES(1,24,'AUTISM','Existe fácil acceso por parte de los profesionales hacia el equipo directivo',1);
INSERT INTO evidences VALUES(2,24,'AUTISM','Se registran y valoran las propuestas de intervención / organización provenientes de los profesionales',1);
INSERT INTO evidences VALUES(3,24,'AUTISM','Se promueve el desarrollo profesional a través del ajuste de los puestos de trabajo a las expectativas profesionales',1);
INSERT INTO evidences VALUES(4,24,'AUTISM','Se solicitan aportaciones de los profesionales referidas a distintos proyectos de la entidad',1);
INSERT INTO indicators VALUES(25,'AUTISM','Las responsabilidades de los profesionales son coherentes con sus funciones',1);
INSERT INTO evidences VALUES(1,25,'AUTISM','Existe un documento que especifica la estructura del personal de la Organización, así como las funciones de cada uno de los profesionales',1);
INSERT INTO evidences VALUES(2,25,'AUTISM','Cada profesional conoce sus funciones',1);
INSERT INTO evidences VALUES(3,25,'AUTISM','Las funciones se adecuan a su perfil profesional',1);
INSERT INTO evidences VALUES(4,25,'AUTISM','Las responsabilidades se ajustan a las funciones',1);
INSERT INTO indicators VALUES(26,'AUTISM','Se promueve la participación y el trabajo en equipo',4);
INSERT INTO evidences VALUES(1,26,'AUTISM','La estructura de la Organización contempla el funcionamiento a través de equipos de trabajo',1);
INSERT INTO evidences VALUES(2,26,'AUTISM','Existen grupos de mejora para evaluar periódicamente y hacer propuestas de mejora en la Organización',1);
INSERT INTO evidences VALUES(3,26,'AUTISM','Existen oportunidades de abordar en grupo estrategias puntuales de intervención, o de apoyo a la intervención, planteadas por cualquier profesional',1);
INSERT INTO evidences VALUES(4,26,'AUTISM','La dirección de la Organización promueve la estabilidad del equipo de profesionales',1);
INSERT INTO indicators VALUES(27,'AUTISM','Se favorece un aumento del nivel de satisfacción en los profesionales',1);
INSERT INTO evidences VALUES(1,27,'AUTISM','Existen vías para analizar el nivel de satisfacción y motivación profesional: encuestas, entrevistas personales',1);
INSERT INTO evidences VALUES(2,27,'AUTISM','Se reconocen, valoran y difunden las Buenas Prácticas Profesionales desarrolladas en la Organización',1);
INSERT INTO evidences VALUES(3,27,'AUTISM','Además del aseguramiento de los mínimos legales referidos a contratación, remuneración, seguridad, la Organización valora y promueve las cuestiones que inciden de forma específica en la satisfacción y motivación de cada profesional',1);
INSERT INTO evidences VALUES(4,27,'AUTISM','Se planifican estrategias a largo plazo, que inciden directamente sobre la satisfacción de los profesionales',1);
INSERT INTO indicators VALUES(28,'AUTISM','Los profesionales están implicados en la Organización',1);
INSERT INTO evidences VALUES(1,28,'AUTISM','Se implica a todo el personal en los procesos de mejora',1);
INSERT INTO evidences VALUES(2,28,'AUTISM','Los profesionales, junto a la entidad titular, participan en la toma de decisiones organizativas y/o de planificación',1);
INSERT INTO evidences VALUES(3,28,'AUTISM','Existe información sobre los proyectos de la Organización',1);
INSERT INTO evidences VALUES(4,28,'AUTISM','Existen posibilidades de implicación en los proyectos de la Organización',1);
INSERT INTO indicators VALUES(29,'AUTISM','Se evalúan las necesidades y deseos de las personas con TEA en los distintos ámbitos de intervención',4);
INSERT INTO evidences VALUES(1,29,'AUTISM','Existe un sistema de recogida de datos sobre las necesidades de la persona con TEA en los distintos ámbitos de intervención',1);
INSERT INTO evidences VALUES(2,29,'AUTISM','Para la realización de cada Plan de Desarrollo Personal, se realiza una revisión de informes y evaluaciones previas en la que intervienen profesionales y personas del entorno socio-familiar.',1);
INSERT INTO evidences VALUES(3,29,'AUTISM','En la valoración de las necesidades participan todos los profesionales y se implica a la familia, que tiene la posibilidad de plantear qué objetivos considera más necesarios en la formación de sus hijos o hijas',1);
INSERT INTO evidences VALUES(4,29,'AUTISM','En la valoración de las necesidades se implica a la persona con TEA a través de distintas modalidades comunicativas o apoyos personalizados',1);
INSERT INTO indicators VALUES(30,'AUTISM','Se elaboran planes de intervención adaptados a las necesidades específicas a lo largo de toda la vida',3);
INSERT INTO evidences VALUES(1,30,'AUTISM','Se elaboran planes individualizados de intervención ante conductas problemáticas o necesidades específicas',1);
INSERT INTO evidences VALUES(2,30,'AUTISM','Los planes específicos de intervención se consensúan entre todos los profesionales que están en contacto con la persona con TEA, con su familia, y con la propia persona con TEA, siempre que sea posible, a través de distintas vías o instrumentos adaptados',1);
INSERT INTO evidences VALUES(3,30,'AUTISM','Las personas implicadas en prestar apoyo a las personas con TEA están coordinadas en el uso de pautas específicas de intervención en diferentes ámbitos (conducta, rehabilitación funcional motora, corrección postural, comunicación)',1);
INSERT INTO evidences VALUES(4,30,'AUTISM','Las actividades se adaptan y estructuran de forma que se garantice el éxito y su realización de la forma más autónoma posible',1);
INSERT INTO indicators VALUES(31,'AUTISM','La estructura de la Programación General de la Organización se adapta a las características de las personas con TEA',2);
INSERT INTO evidences VALUES(1,31,'AUTISM','Existe una Programación General que engloba todos los ámbitos de intervención y que sirve de referente para realizar los Planes de Desarrollo Personal',1);
INSERT INTO evidences VALUES(2,31,'AUTISM','Los contenidos de la Programación General se evalúan periódicamente, y se modifican si se considera necesario',1);
INSERT INTO evidences VALUES(3,31,'AUTISM','Los objetivos de trabajo que promueve cada Plan de Desarrollo Personal son operativos',1);
INSERT INTO evidences VALUES(4,31,'AUTISM','Existe un análisis que evidencia la funcionalidad de los objetivos y aprendizajes programados',1);
INSERT INTO indicators VALUES(32,'AUTISM','Se adecua el proceso de elaboración de los Planes de Desarrollo Personal a las características de las personas con TEA',4);
INSERT INTO evidences VALUES(1,32,'AUTISM','Existe un proceso de elaboración de los Planes de Desarrollo Personal en el que participan todos los profesionales que están en contacto con la persona con TEA, la familia, y la propia persona con TEA, siempre que sea posible, a través de distintas vías o instrumentos adaptados',1);
INSERT INTO evidences VALUES(2,32,'AUTISM','El Plan de Desarrollo Personal detalla los objetivos y metas, y los apoyos necesarios para su consecución',1);
INSERT INTO evidences VALUES(3,32,'AUTISM','Se planifican revisiones periódicas tanto de los objetivos como de las necesidades de apoyo de cada persona con TEA',1);
INSERT INTO evidences VALUES(4,32,'AUTISM','Existe flexibilidad y posibilidad de introducir nuevos objetivos o metas y/o modificar el tipo o grado de apoyo, cuando el Plan de Desarrollo Personal ya está en marcha',1);
INSERT INTO indicators VALUES(33,'AUTISM','Existe un proceso de planificación y captación de apoyos que responde a las características de cada persona',3);
INSERT INTO evidences VALUES(1,33,'AUTISM','La planificación de apoyos tiene en cuenta las aportaciones de la propia persona con TEA, siempre que sea posible, y de las personas de referencia en sus diferentes contextos vitales (familias, profesionales, amigos, conocidos)',1);
INSERT INTO evidences VALUES(2,33,'AUTISM','Se promueve la captación de apoyos naturales',1);
INSERT INTO evidences VALUES(3,33,'AUTISM','La definición de apoyos tiene en cuenta el que la persona pueda conseguir sus objetivos y metas en los distintos ámbitos y contextos vitales',1);
INSERT INTO evidences VALUES(4,33,'AUTISM','Se dota a la persona de estrategias y habilidades que le permitan ejercer cambios y un control del entorno (elecciones, expresión de necesidades, resolución de problemas)',1);
INSERT INTO indicators VALUES(34,'AUTISM','Los criterios metodológicos se adaptan a las necesidades y capacidades de la persona con TEA',2);
INSERT INTO evidences VALUES(1,34,'AUTISM','Existe unidad de criterios en los distintos servicios, programas o contextos donde participa la persona',1);
INSERT INTO evidences VALUES(2,34,'AUTISM','La definición y especificación de objetivos o metas personales facilita la interpretación objetiva, tanto en su ejecución como en su evaluación por parte de todos los profesionales',1);
INSERT INTO evidences VALUES(3,34,'AUTISM','Se tiene en cuenta la generalización de aprendizajes',1);
INSERT INTO evidences VALUES(4,34,'AUTISM','Los objetivos o metas permiten planificar nuevos aprendizajes',1);
INSERT INTO indicators VALUES(35,'AUTISM','Los Planes de Desarrollo Personal se adaptan a la persona',3);
INSERT INTO evidences VALUES(1,35,'AUTISM','Se abordan todas las necesidades en las diferentes áreas de desarrollo personal y social de la persona con TEA',1);
INSERT INTO evidences VALUES(2,35,'AUTISM','El Plan de Desarrollo Personal favorece el que la persona con TEA participe y realice actividades adaptadas a sus gustos y capacidades, en distintos contextos, favoreciendo siempre la mayor inclusión posible',1);
INSERT INTO evidences VALUES(3,35,'AUTISM','Se contemplan diferentes opciones, adecuadas a los diferentes niveles de adaptación y capacidades',1);
INSERT INTO evidences VALUES(4,35,'AUTISM','Existen programas en función de las diferentes etapas evolutivas (escolares, adultos...)',1);
INSERT INTO indicators VALUES(36,'AUTISM','Existe un seguimiento y evaluación continua de cada Plan de Desarrollo Personal',3);
INSERT INTO evidences VALUES(1,36,'AUTISM','Existen informes de evaluación individual de cada persona con TEA',1);
INSERT INTO evidences VALUES(2,36,'AUTISM','Se realizan orientaciones y propuestas de intervención futura (Plan de Desarrollo Futuro Personal) basadas en la evaluación',1);
INSERT INTO evidences VALUES(3,36,'AUTISM','Existen instrumentos de evaluación basados en las dimensiones de calidad de vida',1);
INSERT INTO evidences VALUES(4,36,'AUTISM','Los resultados de la evaluación y el seguimiento provienen tanto de todos los profesionales que están en contacto con la persona con TEA, como de su familia, conocidos y otras personas de referencia en su vida',1);
INSERT INTO indicators VALUES(37,'AUTISM','Se asegura una formación inicial a los nuevos profesionales',2);
INSERT INTO evidences VALUES(1,37,'AUTISM','Existe un procedimiento de información, formación y apoyo a nuevos profesionales, personas que prestan apoyo natural, voluntarios, alumnos en prácticas',1);
INSERT INTO evidences VALUES(2,37,'AUTISM','Se organizan acciones formativas en las que participan los nuevos profesionales, voluntarios, alumnos en prácticas',1);
INSERT INTO evidences VALUES(3,37,'AUTISM','Existe una documentación formativa inicial que contiene información sobre autismo, programas y pautas de intervención',1);
INSERT INTO evidences VALUES(4,37,'AUTISM','Cada nuevo profesional tiene asignado un profesional-tutor que se responsabiliza de su formación y seguimiento, y una ficha personal de formación donde se recoge toda su trayectoria formativa y profesional',1);
INSERT INTO indicators VALUES(38,'AUTISM','La formación incluye aspectos técnicos, organizacionales y valores de la organización',1);
INSERT INTO evidences VALUES(1,38,'AUTISM','Existe un documento sobre la naturaleza, finalidad, valores, objetivos y principios éticos de la organización difundido a todos los profesionales',1);
INSERT INTO evidences VALUES(2,38,'AUTISM','Todos los profesionales tienen acceso a la política de la Organización, los planes de mejora y el sistema de gestión de calidad',1);
INSERT INTO evidences VALUES(3,38,'AUTISM','Existen canales de información para transmitir los fines, valores y objetivos de la Organización',1);
INSERT INTO evidences VALUES(4,38,'AUTISM','Se ofrece información periódica sobre los proyectos y trayectoria de la Organización',1);
INSERT INTO indicators VALUES(39,'AUTISM','Cada profesional recibe una formación específica sobre su puesto de trabajo',2);
INSERT INTO evidences VALUES(1,39,'AUTISM','El plan de formación incluye acciones específicas para puestos de trabajo concretos',1);
INSERT INTO evidences VALUES(2,39,'AUTISM','Todos los profesionales en su proceso de formación inicial reciben una formación específica referida a su puesto de trabajo, en la que se promueve la participación activa y el aprendizaje significativo',1);
INSERT INTO evidences VALUES(3,39,'AUTISM','Todos los profesionales tienen posibilidades, de forma periódica, de actualizar o ampliar la formación referida a su puesto de trabajo',1);
INSERT INTO evidences VALUES(4,39,'AUTISM','La Organización promueve el que se realicen proyectos o iniciativas encaminadas a la formación específica o especialización: intercambios profesionales, grupos de trabajo específicos',1);
INSERT INTO indicators VALUES(40,'AUTISM','Se promueve la formación continua, la actualización y el desarrollo profesional',2);
INSERT INTO evidences VALUES(1,40,'AUTISM','Existe un plan de formación anual en el que se incluyen necesidades y demandas planteadas por los profesionales',1);
INSERT INTO evidences VALUES(2,40,'AUTISM','La Organización colabora con otras entidades (Universidad), en proyectos de investigación y avance científico',1);
INSERT INTO evidences VALUES(3,40,'AUTISM','Todos los profesionales participan de forma periódica en acciones de formación realizadas por la propia entidad o por otras Organizaciones',1);
INSERT INTO evidences VALUES(4,40,'AUTISM','Existe un procedimiento que evalúa periódicamente el desempeño profesional',1);
INSERT INTO indicators VALUES(41,'AUTISM','La entidad cuenta con recursos propios que favorecen la formación, actualización y desarrollo profesional',1);
INSERT INTO evidences VALUES(1,41,'AUTISM','Existe un programa de formación interna (cursos, reuniones de formación, intercambios con otras organizaciones...)',1);
INSERT INTO evidences VALUES(2,41,'AUTISM','Existe posibilidad de acceso a vías de actualización: nuevas publicaciones específicas, investigación, bibliografía actualizada, Internet',1);
INSERT INTO evidences VALUES(3,41,'AUTISM','Existe un sistema de gestión del conocimiento por el que toda la formación e información recibida por un profesional se difunde y se hace accesible al resto del equipo',1);
INSERT INTO evidences VALUES(4,41,'AUTISM','La Organización mide el impacto de la formación profesional, recogiendo indicios de cómo la formación recibida por los profesionales produce cambios organizativos',1);
INSERT INTO indicators VALUES(42,'AUTISM','La actuación de cada profesional tiene como referencia los principios de la Planificación Centrada en la Persona',4);
INSERT INTO evidences VALUES(1,42,'AUTISM','El profesional promueve un conocimiento profundo de la persona con TEA, sus expectativas, metas en los distintos contextos vitales',1);
INSERT INTO evidences VALUES(2,42,'AUTISM','El profesional impulsa la creación de Círculos de Apoyo que puedan implicar al resto de profesionales, familias, amigos para definir y poner en marcha el Plan de Desarrollo Personal',1);
INSERT INTO evidences VALUES(3,42,'AUTISM','El profesional coordina y motiva la función que cada persona del Círculo de Apoyo ha asumido',1);
INSERT INTO evidences VALUES(4,42,'AUTISM','El profesional promueve la captación de apoyos, especialmente naturales, para facilitar el Plan de Desarrollo Personal',1);
INSERT INTO indicators VALUES(43,'AUTISM','Existe una información individualizada de cada persona con TEA',4);
INSERT INTO evidences VALUES(1,43,'AUTISM','Se utilizan instrumentos que permiten obtener datos significativos para la elaboración de cada perfil personal, incidiendo en preferencias, necesidades, visión de futuro',1);
INSERT INTO evidences VALUES(2,43,'AUTISM','Existe documentación personalizada (pautas específicas, gustos e intereses, capacidades, mapa de relaciones sociales...)',1);
INSERT INTO evidences VALUES(3,43,'AUTISM','Existe un proceso que facilita el conocimiento de la persona con TEA y que contempla, tanto la información que ésta nos pueda aportar de forma directa sobre su vida y expectativas de futuro, como la que puedan aportarnos las personas mas significativas de sus contextos vitales (profesionales, familia, amigos)',1);
INSERT INTO evidences VALUES(4,43,'AUTISM','Existen fichas personales de registro y seguimiento de las habilidades y metas que se trabajan con cada persona',1);
INSERT INTO indicators VALUES(44,'AUTISM','Se conoce en profundidad y de forma integral a la persona con TEA',3);
INSERT INTO evidences VALUES(1,44,'AUTISM','Todos los profesionales conocen la información básica detallada referida a cada persona con TEA (pautas de intervención, gustos e intereses, nivel de capacidades...), y participan en el proceso de revisión y actualización de la misma',1);
INSERT INTO evidences VALUES(2,44,'AUTISM','Se utilizan instrumentos que permiten obtener datos significativos para la elaboración de cada Plan de Desarrollo Personal, incidiendo en preferencias, necesidades, visión de futuro',1);
INSERT INTO evidences VALUES(3,44,'AUTISM','Se obtiene información directa de las personas con TEA y de las de personas cercanas y que comparten experiencias vitales con ella: familias, profesionales, conocidos',1);
INSERT INTO evidences VALUES(4,44,'AUTISM','La información de la que se dispone se refiere a todas las áreas y ámbitos vitales de la persona, y tiene en cuenta capacidades, gustos e intereses, necesidades y apoyos que requiere, pautas específicas de intervención, aspectos que le desagradan',1);
INSERT INTO indicators VALUES(45,'AUTISM','La intervención es adaptada a cada Plan de Desarrollo Personal (características, necesidades, deseos, preferencias de la persona)',3);
INSERT INTO evidences VALUES(1,45,'AUTISM','Las posibilidades de elección que tienen las personas con TEA tienen en cuenta sus gustos, intereses, visión de futuro',1);
INSERT INTO evidences VALUES(2,45,'AUTISM','Los objetivos que se plantean con cada persona con TEA son funcionales, tienen un impacto positivo en su Plan de Desarrollo Personal',1);
INSERT INTO evidences VALUES(3,45,'AUTISM','Las actividades, interacción y materiales están adaptados a las capacidades y edad cronológica',1);
INSERT INTO evidences VALUES(4,45,'AUTISM','Se tienen en cuenta las necesidades individuales de apoyo específico y/o especializado',1);
INSERT INTO indicators VALUES(46,'AUTISM','La práctica y actitudes profesionales tienen como referente la misión y los valores de la organización',2);
INSERT INTO evidences VALUES(1,46,'AUTISM','Todos los profesionales conocen y comparten la misión y los valores de la Organización',1);
INSERT INTO evidences VALUES(2,46,'AUTISM','La Organización cuenta con un Código Ético que concreta su misión/visión y define los valores y principios que deben presidir la práctica profesional',1);
INSERT INTO evidences VALUES(3,46,'AUTISM','Todos los profesionales conocen, aceptan y se comprometen a regular su práctica basándose en el Código Ético',1);
INSERT INTO evidences VALUES(4,46,'AUTISM','Existe un sistema de reflexión que persigue integrar en la práctica profesional los valores y actitudes consensuados en el Código Ético y evaluar periódicamente en que medida esto es así',1);
INSERT INTO indicators VALUES(47,'AUTISM','La Organización promueve la ampliación del conocimiento a través de la participación activa en investigaciones sobre TEA',2);
INSERT INTO evidences VALUES(1,47,'AUTISM','Se mantienen convenios de colaboración con instituciones universitarias y científicas que realizan investigación sobre TEA',1);
INSERT INTO evidences VALUES(2,47,'AUTISM','La Organización promueve investigaciones sobre TEA basadas en protocolos definidos en guías de buenas prácticas',1);
INSERT INTO evidences VALUES(3,47,'AUTISM','La Organización colabora en investigaciones sobre TEA.',1);
INSERT INTO evidences VALUES(4,47,'AUTISM','Se aplican o incorporan en la práctica los resultados de investigaciones científicamente contrastadas',1);
INSERT INTO indicators VALUES(48,'AUTISM','La configuración de los grupos de compañeros se adaptan a las necesidades de las personas con TEA',1);
INSERT INTO evidences VALUES(1,48,'AUTISM','Existen criterios que justifican los grupos de compañeros en los que se integran las personas con TEA: edad, capacidades, preferencias',1);
INSERT INTO evidences VALUES(2,48,'AUTISM','Se analiza periódicamente la interacción entre los componentes de los agrupamientos',1);
INSERT INTO evidences VALUES(3,48,'AUTISM','Se detectan y resuelven los posibles conflictos y/o incompatibilidades detectadas',1);
INSERT INTO evidences VALUES(4,48,'AUTISM','Existe flexibilidad, favoreciendo la elección, para realizar nuevos grupos de compañeros ante situaciones o actividades puntuales o imprevistos',1);
INSERT INTO indicators VALUES(49,'AUTISM','Los profesionales son una referencia clara para las personas con TEA',2);
INSERT INTO evidences VALUES(1,49,'AUTISM','Cada persona con TEA dispone de un profesional-tutor de referencia',1);
INSERT INTO evidences VALUES(2,49,'AUTISM','Se analiza periódicamente la relación y la adecuación del perfil humano y profesional a las características y preferencias de la persona con TEA, existiendo posibilidad de cambio de profesional de referencia',1);
INSERT INTO evidences VALUES(3,49,'AUTISM','Existen unos criterios de asignación de los profesionales a la persona con TEA',1);
INSERT INTO evidences VALUES(4,49,'AUTISM','Los profesionales-tutores canalizan toda la información pertinente sobre la persona con TEA, y coordinan las intervenciones y la prestación de apoyos en los distintos contextos',1);
INSERT INTO indicators VALUES(50,'AUTISM','Existe una organización de las tareas y actividades',3);
INSERT INTO evidences VALUES(1,50,'AUTISM','Existe una amplia variedad de tareas y actividades que responde a criterios de funcionalidad, significatividad, motivacionales y formativos',1);
INSERT INTO evidences VALUES(2,50,'AUTISM','Las actividades abarcan tanto los diferentes niveles aptitudinales como los intereses de las personas que participan en ellas',1);
INSERT INTO evidences VALUES(3,50,'AUTISM','Se estructuran los distintos procesos de trabajo asignando a cada persona responsabilidades dentro de los mismos',1);
INSERT INTO evidences VALUES(4,50,'AUTISM','Existe una secuenciación de los pasos de las actividades, que aporta una información clara hacia adelante y hacia atrás acerca de las mismas, a modo de instrucciones de trabajo comprensibles',1);
INSERT INTO indicators VALUES(51,'AUTISM','Las personas con TEA tienen asignadas responsabilidades y participan activamente en la Organización',1);
INSERT INTO evidences VALUES(1,51,'AUTISM','Cada persona con TEA tiene asignadas responsabilidades en la Organización, adecuadas a sus capacidades e intereses',1);
INSERT INTO evidences VALUES(2,51,'AUTISM','Existe una revisión periódica que evalúa el grado de adecuación y desarrollo de las responsabilidades asignadas y facilita el reconocimiento hacia las personas con TEA',1);
INSERT INTO evidences VALUES(3,51,'AUTISM','Existen foros, instrumentos, para recoger aportaciones y sugerencias de las personas con TEA',1);
INSERT INTO evidences VALUES(4,51,'AUTISM','Las personas con TEA participan, siempre que sea posible, en el proceso de diseño, creación y adaptación de materiales y actividades',1);
INSERT INTO indicators VALUES(52,'AUTISM','Se dispone de apoyo y seguimiento técnico integrado en el equipo profesional',2);
INSERT INTO evidences VALUES(1,52,'AUTISM','Se dispone de personal técnico especializado en las diferentes áreas de intervención',1);
INSERT INTO evidences VALUES(2,52,'AUTISM','El trabajo técnico está integrado dentro del equipo, de forma que se planifican y se abordan en grupo las estrategias de intervención individualizadas',1);
INSERT INTO evidences VALUES(3,52,'AUTISM','Existe un sistema de evaluación continua de los programas específicos de intervención',1);
INSERT INTO evidences VALUES(4,52,'AUTISM','El seguimiento técnico detecta regularmente nuevas necesidades de intervención o apoyos específicos',1);
INSERT INTO indicators VALUES(53,'AUTISM','El horario y ritmo de trabajo de las personas con TEA se adapta a sus necesidades',3);
INSERT INTO evidences VALUES(1,53,'AUTISM','Existen horarios estables y personalizados',1);
INSERT INTO evidences VALUES(2,53,'AUTISM','Se informa anticipadamente a cada persona con TEA del horario que tiene, así como de los posibles cambios e imprevistos',1);
INSERT INTO evidences VALUES(3,53,'AUTISM','Se contemplan tiempos en los que cada persona puede desarrollar actividades de libre elección',1);
INSERT INTO evidences VALUES(4,53,'AUTISM','Existe un ajuste entre tiempo y ritmo de trabajo, y tiempo de descanso',1);
INSERT INTO indicators VALUES(54,'AUTISM','El horario y distribución de tiempos de los profesionales se adecua a las necesidades de las personas con TEA',3);
INSERT INTO evidences VALUES(1,54,'AUTISM','Existe un horario estable y predecible',1);
INSERT INTO evidences VALUES(2,54,'AUTISM','El horario y distribución de tiempos garantiza una organización de los tiempos de tránsito entre actividades, de descanso, de entradas y salidas',1);
INSERT INTO evidences VALUES(3,54,'AUTISM','Ante cambios e imprevistos existe un sistema de reorganización que no afecta a las personas con TEA',1);
INSERT INTO evidences VALUES(4,54,'AUTISM','Se rentabilizan los recursos personales asignando a los profesionales tareas y funciones, que inciden en la mejora de la calidad del servicio, en los momentos en que no sea necesaria o no tengan asignada atención directa',1);
INSERT INTO indicators VALUES(55,'AUTISM','Se facilita la comunicación entre todas las personas vinculadas a la Organización',2);
INSERT INTO evidences VALUES(1,55,'AUTISM','Existen vías de comunicación formal entre personas vinculadas a la Organización según el ámbito, los implicados y el tema',1);
INSERT INTO evidences VALUES(2,55,'AUTISM','Es posible el acceso de todos los profesionales de la Organización a los diferentes canales de comunicación',1);
INSERT INTO evidences VALUES(3,55,'AUTISM','Se utilizan diferentes sistemas, vías y apoyos para promover la comunicación de las personas con TEA',1);
INSERT INTO evidences VALUES(4,55,'AUTISM','Existen canales que posibilitan una comunicación continua y rápida entre programas y servicios, con la familia o tutores, y otras personas relacionadas con la persona con TEA',1);
INSERT INTO indicators VALUES(56,'AUTISM','Se planifican y promueven tiempos y espacios para la coordinación',2);
INSERT INTO evidences VALUES(1,56,'AUTISM','Existen tiempos programados para reuniones y coordinación',1);
INSERT INTO evidences VALUES(2,56,'AUTISM','Existe una organización y una dinámica que facilita la eficacia de las reuniones: orden del día previo, coordinador, acta, distribución de tiempos por temas',1);
INSERT INTO evidences VALUES(3,56,'AUTISM','Existe la posibilidad de participar activamente en las reuniones e incorporar temas por parte de todos los participantes',1);
INSERT INTO evidences VALUES(4,56,'AUTISM','Se realiza un seguimiento de la eficacia de las conclusiones y decisiones que se toman en las reuniones',1);
INSERT INTO indicators VALUES(57,'AUTISM','Existe coordinación con otros programas y servicios relacionados con la persona con TEA',2);
INSERT INTO evidences VALUES(1,57,'AUTISM','Existen vías de coordinación entre los diferentes servicios de la Organización',1);
INSERT INTO evidences VALUES(2,57,'AUTISM','Existen vías de coordinación con otros servicios externos a la Organización, pero relacionados con los programas que se promueven desde ésta',1);
INSERT INTO evidences VALUES(3,57,'AUTISM','Existe un proceso que garantiza la gestión y difusión de la información, y el conocimiento a todas las personas, servicios o entidades relacionados con la persona con TEA',1);
INSERT INTO evidences VALUES(4,57,'AUTISM','Existe un registro de información e incidencias de cada servicio que centralice la información y ayude a coordinarse con el resto de servicios',1);
INSERT INTO indicators VALUES(58,'AUTISM','Se facilita la comunicación a las personas con TEA',4);
INSERT INTO evidences VALUES(1,58,'AUTISM','Cada persona con TEA tiene definido qué sistema de comunicación utiliza, adaptado a sus características ó perfil individual',1);
INSERT INTO evidences VALUES(2,58,'AUTISM','Todos los profesionales, familias y personas que forman parte de la vida de la persona con TEA, tienen acceso al conocimiento y uso de los sistemas de comunicación que ésta utiliza',1);
INSERT INTO evidences VALUES(3,58,'AUTISM','Se utilizan los sistemas de comunicación con diferentes objetivos: anticipar, facilitar peticiones y deseos, informar, elegir, rechazar',1);
INSERT INTO evidences VALUES(4,58,'AUTISM','Existe coherencia entre los soportes de estructuración y los de comunicación que se utilizan con cada persona',1);
INSERT INTO indicators VALUES(59,'AUTISM','Se realiza una evaluación interna de la Organización',2);
INSERT INTO evidences VALUES(1,59,'AUTISM','Existen instrumentos para evaluar internamente, de forma periódica, la Organización',1);
INSERT INTO evidences VALUES(2,59,'AUTISM','En la evaluación participan todos los profesionales de la Organización, las familias y, en los casos en que sea posible, las personas con TEA, y sus resultados se difunden a todos ellos',1);
INSERT INTO evidences VALUES(3,59,'AUTISM','Existe una vía para registrar de forma inmediata puntos débiles o situaciones susceptibles de mejora en la Organización',1);
INSERT INTO evidences VALUES(4,59,'AUTISM','Se analiza e implantan acciones de mejora tras la detección de puntos débiles en la organización.',1);
INSERT INTO indicators VALUES(60,'AUTISM','La mejora de la Organización contempla una evaluación externa',1);
INSERT INTO evidences VALUES(1,60,'AUTISM','En la Organización se realizan evaluaciones externas de forma periódica',1);
INSERT INTO evidences VALUES(2,60,'AUTISM','En la evaluación está implicada toda la Organización: profesionales, familias y, en los casos en que sea posible, las personas con TEA',1);
INSERT INTO evidences VALUES(3,60,'AUTISM','Los resultados de las evaluaciones se difunden a todas las personas implicadas',1);
INSERT INTO evidences VALUES(4,60,'AUTISM','De la evaluación externa se derivan mejoras en la Organización',1);
INSERT INTO indicators VALUES(61,'AUTISM','La dirección de la Organización impulsa la mejora continua',3);
INSERT INTO evidences VALUES(1,61,'AUTISM','La Organización cuenta con un Plan Estratégico que contempla la elaboración de Planes Anuales de Mejora',1);
INSERT INTO evidences VALUES(2,61,'AUTISM','La dirección motiva e involucra a los profesionales para que propongan y desarrollen acciones de mejora',1);
INSERT INTO evidences VALUES(3,61,'AUTISM','La dirección está implicada activamente en las acciones de mejora propuestas',1);
INSERT INTO evidences VALUES(4,61,'AUTISM','La dirección reconoce los esfuerzos y logros de mejora realizados por las personas que la integran',1);
INSERT INTO indicators VALUES(62,'AUTISM','Se rentabilizan los recursos humanos',2);
INSERT INTO evidences VALUES(1,62,'AUTISM','El número de profesionales es suficiente y se adecua a las necesidades de ratio de cada persona con TEA y/o actividad',1);
INSERT INTO evidences VALUES(2,62,'AUTISM','Existe una asignación clara de funciones a los profesionales',1);
INSERT INTO evidences VALUES(3,62,'AUTISM','Existen profesionales especializados en las diferentes áreas',1);
INSERT INTO evidences VALUES(4,62,'AUTISM','Existe una organización del personal no contratado: voluntarios, alumnos en prácticas..., con asignación de tiempos y tareas a realizar, y que cumple con la normativa que regula sus derechos y deberes',1);
INSERT INTO indicators VALUES(63,'AUTISM','Existe una adecuada organización del trabajo de los profesionales',3);
INSERT INTO evidences VALUES(1,63,'AUTISM','Existe una estructuración clara de los tiempos, actividades y agrupamientos asignados a cada profesional a lo largo de toda la jornada',1);
INSERT INTO evidences VALUES(2,63,'AUTISM','Existen tiempos de intervención dedicados a desarrollar programas específicos, individualizados y con profesionales especializados',1);
INSERT INTO evidences VALUES(3,63,'AUTISM','Existe posibilidad de compatibilizar distintas situaciones de trabajo: individual, grupal..., según las necesidades de las personas con TEA y del momento',1);
INSERT INTO evidences VALUES(4,63,'AUTISM','Existe una definición y asignación de responsabilidades y funciones dentro de la Organización que implica a todos los profesionales',1);
INSERT INTO indicators VALUES(64,'AUTISM','Se rentabilizan los recursos materiales',1);
INSERT INTO evidences VALUES(1,64,'AUTISM','Los recursos están a disposición de todos los profesionales',1);
INSERT INTO evidences VALUES(2,64,'AUTISM','Se incorporan o generan nuevos materiales de apoyo a la intervención, según surgen las necesidades de las personas con TEA',1);
INSERT INTO evidences VALUES(3,64,'AUTISM','Se utilizan ayudas técnicas y sistemas de seguridad en las máquinas para facilitar el acceso del mayor número posible de personas con TEA a las mismas',1);
INSERT INTO evidences VALUES(4,64,'AUTISM','Los recursos están puestos al día, en buen estado, son funcionales, y apropiados a la edad y a las necesidades ergonómicas de cada persona con TEA',1);
INSERT INTO indicators VALUES(65,'AUTISM','El entorno físico favorece la participación, accesibilidad y la autonomía de las personas con TEA',3);
INSERT INTO evidences VALUES(1,65,'AUTISM','Existen suficientes espacios y se adecuan de forma flexible a las necesidades puntuales y cambiantes que pueden tener las personas con TEA',1);
INSERT INTO evidences VALUES(2,65,'AUTISM','La información / estructuración espacial facilita la comprensión y el desenvolvimiento de la forma más autónoma y segura posible de las personas con TEA',1);
INSERT INTO evidences VALUES(3,65,'AUTISM','Se minimizan las barreras arquitectónicas o dificultades de acceso para las personas con TEA',1);
INSERT INTO evidences VALUES(4,65,'AUTISM','Los espacios se adecuan a la normativa que regula la construcción y favorecen aspectos como la salud y la higiene',1);
INSERT INTO indicators VALUES(66,'AUTISM','Existen alianzas de colaboración con otras entidades, pertenezcan o no al sector de la discapacidad',1);
INSERT INTO evidences VALUES(1,66,'AUTISM','Existen vínculos / convenios con otros recursos y/o entidades relacionadas con los objetivos del servicio',1);
INSERT INTO evidences VALUES(2,66,'AUTISM','Existen vínculos / convenios con servicios de salud, trabajo y bienestar social',1);
INSERT INTO evidences VALUES(3,66,'AUTISM','Existen vínculos / convenios con otros recursos de la comunidad en general: empresas, deportes, ocio',1);
INSERT INTO evidences VALUES(4,66,'AUTISM','Existe un programa de captación, formación y seguimiento del voluntariado',1);
INSERT INTO indicators VALUES(67,'AUTISM','La Organización asume un compromiso de responsabilidad social',2);
INSERT INTO evidences VALUES(1,67,'AUTISM','La Organización contrata servicios o adquiere productos necesarios, realizando una discriminación positiva hacia entidades o empresas que integren personas en riesgo de exclusión social',1);
INSERT INTO evidences VALUES(2,67,'AUTISM','La Organización garantiza la calidad de los productos o servicios contratados a personas o empresas ajenas, y las condiciones en que estos han sido realizados',1);
INSERT INTO evidences VALUES(3,67,'AUTISM','La Organización cuenta con procesos de respeto al medio ambiente: gestión de residuos, reciclado de productos',1);
INSERT INTO evidences VALUES(4,67,'AUTISM','Los espacios carecen de las barreras arquitectónicas estipuladas por la normativa, permitiendo el acceso y la autonomía a personas con distintas discapacidades',1);
INSERT INTO indicators VALUES(68,'AUTISM','Se favorece la sensibilización y la imagen social positiva',2);
INSERT INTO evidences VALUES(1,68,'AUTISM','Se organizan actividades dirigidas y abiertas a toda la comunidad (exposiciones, jornadas, conferencias)',1);
INSERT INTO evidences VALUES(2,68,'AUTISM','Se participa en proyectos, iniciativas promovidas desde los diferentes recursos de la comunidad, cuyo objetivo prioritario es la presencia y sensibilización social',1);
INSERT INTO evidences VALUES(3,68,'AUTISM','Existen un plan o proceso para dar respuesta a las demandas externas de información, visitas, intercambios, formación',1);
INSERT INTO evidences VALUES(4,68,'AUTISM','Existe un Plan de Comunicación Externa para divulgar y ofrecer información sobre autismo: folletos de divulgación, página Web, publicaciones, videos, aparición en medios de comunicación',1);

INSERT INTO users VALUES('pahita@ubu.es','ADMIN','123456','Pablo','Ahíta del Barrio',622817551);
--CREATE LOGIN [pahita@ubu.es] WITH PASSWORD = '123456';
INSERT INTO users VALUES('jlcgomez@ubu.es','ORGANIZATION','123456','Jose Luis','Cuesta Gómez',-1);
--CREATE LOGIN [jlcgomez@ubu.es] WITH PASSWORD = '123456';
INSERT INTO users VALUES('mgg@ubu.es','ORGANIZATION','123456','Miguel','Gómez Gentil',-1);
--CREATE LOGIN [mgg@ubu.es] WITH PASSWORD = '123456';

INSERT INTO administrators VALUES('pahita@ubu.es','ADMIN');
INSERT INTO addresses VALUES(1,'Calle','las Rebolledas',-1,-1,'-',09001,'Burgos','Burgos','Castilla y León','Spain');
INSERT INTO organizations VALUES(1,'EVALUATOR','AUTISM','Fundación Miradas',1,'fmiradas@fundacionmiradas.org',947072019,'Fundación Miradas es una organización de ámbito estatal creada en 2013 por la Asociación Autismo Burgos que orienta sus actuaciones a la prestación de apoyos a la capacidad jurídica y a la defensa de los derechos de las personas con autismo y a sus familias, así como a la mejora de la calidad de vida de estas. De igual forma, la Fundación desarrolla diferentes iniciativas que fomenten la sensibilización, el conocimiento, el estudio y la investigación sobre las personas con un TEA y sus familias. En 2015 junto con la Universidad de Burgos, crea la Cátedra Miradas por el Autismo (y enlazas tanto a la UBU y a la Cátedra), la primera que centra su atención en el colectivo de las personas con TEA y sus familias en España.');
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