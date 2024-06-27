using Microsoft.EntityFrameworkCore;
using OTEAServer.Models;

namespace OTEAServer.Misc
{
    /// <summary>
    /// Custom database context for Entity Framework with all the models
    /// Author: Pablo Ahíta del Barrio
    /// Version: 1
    /// </summary>
    public class DatabaseContext : DbContext 
    {
        /// <summary>
        /// Class constructor
        /// </summary>
        /// <param name="options">Database context options</param>
        public DatabaseContext(DbContextOptions<DatabaseContext> options) :base(options) { }


        /// <summary>
        /// Database set for addresses
        /// </summary>
        public DbSet<Address> Addresses { get; set; }

        /// <summary>
        /// Database set for ambits
        /// </summary>
        public DbSet<Ambit> Ambits { get; set; }

        /// <summary>
        /// Database set for centers
        /// </summary>
        public DbSet<Center> Centers { get; set; }

        /// <summary>
        /// Database set for cities
        /// </summary>
        public DbSet<City> Cities { get; set; }

        /// <summary>
        /// Database set for countries
        /// </summary>
        public DbSet<Country> Countries { get; set; }

        /// <summary>
        /// Database set for evaluator teams
        /// </summary>
        public DbSet<EvaluatorTeam> EvaluatorTeams { get; set; }

        /// <summary>
        /// Database set for evidences
        /// </summary>
        public DbSet<Evidence> Evidences { get; set; }

        /// <summary>
        /// Database set for indicators
        /// </summary>
        public DbSet<Indicator> Indicators { get; set; }

        /// <summary>
        /// Database set for indicators evaluations
        /// </summary>
        public DbSet<IndicatorsEvaluation> IndicatorsEvaluations { get; set; }

        /// <summary>
        /// Database set for evidence registers of indicators evaluations
        /// </summary>
        public DbSet<IndicatorsEvaluationEvidenceReg> IndicatorsEvaluationsEvidencesRegs { get; set; }

        /// <summary>
        /// Database set for indicator registers of indicators evaluations
        /// </summary>
        public DbSet<IndicatorsEvaluationIndicatorReg> IndicatorsEvaluationsIndicatorsRegs { get; set; }

        /// <summary>
        /// Database set for organizations
        /// </summary>
        public DbSet<Organization> Organizations { get; set; }

        /// <summary>
        /// Database set for provinces
        /// </summary>
        public DbSet<Province> Provinces { get; set; }

        /// <summary>
        /// Database set for regions
        /// </summary>
        public DbSet<Region> Regions { get; set; }


        /// <summary>
        /// Database set for first level division of ambits
        /// </summary>
        public DbSet<SubAmbit> SubAmbits { get; set; }

        /// <summary>
        /// Database set for second level division of ambits
        /// </summary>
        public DbSet<SubSubAmbit> SubSubAmbits { get; set; }
        
        /// <summary>
        /// Database set for users
        /// </summary>
        public DbSet<User> Users { get; set; }

        /// <summary>
        /// Method that builds all the necessary models to connect correctly to the database
        /// </summary>
        /// <param name="modelBuilder">Model builder</param>
        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<Address>().HasKey(a => new { a.idAddress });
            modelBuilder.Entity<Ambit>().HasKey(a => new { a.idAmbit });
            modelBuilder.Entity<Center>().HasKey(c => new { c.idCenter, c.idOrganization, c.orgType, c.illness });
            modelBuilder.Entity<City>().HasKey(c => new { c.idCity, c.idProvince, c.idRegion, c.idCountry });
            modelBuilder.Entity<Country>().HasKey(c => new { c.idCountry });
            modelBuilder.Entity<EvaluatorTeam>().HasKey(t => new { t.idEvaluatorTeam, t.idEvaluatorOrganization, t.orgTypeEvaluator, t.idEvaluatedOrganization, t.orgTypeEvaluated, t.idCenter, t.illness});
            modelBuilder.Entity<Evidence>().HasKey(e => new { e.idEvidence, e.idSubSubAmbit, e.idSubAmbit, e.idAmbit, e.idIndicator, e.indicatorType, e.indicatorVersion });
            modelBuilder.Entity<Indicator>().HasKey(i => new { i.idSubSubAmbit, i.idSubAmbit, i.idAmbit, i.idIndicator,i.indicatorType,i.indicatorVersion});
            modelBuilder.Entity<IndicatorsEvaluation>().HasKey(e => new { e.evaluationDate, e.idEvaluatorTeam, e.idEvaluatorOrganization, e.orgTypeEvaluator, e.idEvaluatedOrganization, e.orgTypeEvaluated, e.illness, e.idCenter });
            modelBuilder.Entity<IndicatorsEvaluationEvidenceReg>().HasKey(r => new { r.evaluationDate,r.idEvaluatorTeam, r.idEvaluatorOrganization, r.orgTypeEvaluator,r.idEvaluatedOrganization,r.orgTypeEvaluated,r.illness,r.idCenter, r.idSubSubAmbit, r.idSubAmbit, r.idAmbit, r.idIndicator,r.idEvidence,r.indicatorVersion, r.evaluationType });
            modelBuilder.Entity<IndicatorsEvaluationIndicatorReg>().HasKey(r => new { r.evaluationDate, r.idEvaluatorTeam, r.idEvaluatorOrganization, r.orgTypeEvaluator, r.idEvaluatedOrganization, r.orgTypeEvaluated, r.illness, r.idCenter, r.idSubSubAmbit, r.idSubAmbit, r.idAmbit, r.idIndicator, r.indicatorVersion, r.evaluationType });
            modelBuilder.Entity<Organization>().HasKey(o => new { o.idOrganization,o.orgType,o.illness });
            modelBuilder.Entity<Province>().HasKey(p => new { p.idProvince,p.idRegion,p.idCountry });
            modelBuilder.Entity<Region>().HasKey(r => new { r.idRegion,r.idCountry });
            modelBuilder.Entity<SubAmbit>().HasKey(a => new { a.idSubAmbit, a.idAmbit });
            modelBuilder.Entity<SubSubAmbit>().HasKey(a => new { a.idSubSubAmbit, a.idSubAmbit, a.idAmbit });
            modelBuilder.Entity<User>().HasKey(u => new { u.emailUser });
        }


    }
}
