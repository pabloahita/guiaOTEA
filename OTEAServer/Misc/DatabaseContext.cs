using Microsoft.EntityFrameworkCore;
using OTEAServer.Models;

namespace OTEAServer.Misc
{
    public class DatabaseContext : DbContext 
    {
           public DatabaseContext(DbContextOptions<DatabaseContext> options) :base(options) { }



        public DbSet<Address> Addresses { get; set; }

        public DbSet<Ambit> Ambits { get; set; }

        public DbSet<Center> Centers { get; set; }

        public DbSet<City> Cities { get; set; }

        public DbSet<Country> Countries { get; set; }

        public DbSet<EvaluatorTeam> EvaluatorTeams { get; set; }

        public DbSet<Evidence> Evidences { get; set; }

        public DbSet<Indicator> Indicators { get; set; }

        public DbSet<IndicatorsEvaluation> IndicatorsEvaluations { get; set; }

        public DbSet<IndicatorsEvaluationReg> IndicatorsEvaluationsRegs { get; set; }

        public DbSet<Organization> Organizations { get; set; }

        public DbSet<Province> Provinces { get; set; }

        public DbSet<Region> Regions { get; set; }

        public DbSet<Request> Requests { get; set; }

        public DbSet<Session> Sessions { get; set; }

        public DbSet<User> Users { get; set; }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<Address>().HasKey(a => new { a.idAddress });
            modelBuilder.Entity<Ambit>().HasKey(a => new { a.idAmbit });
            modelBuilder.Entity<Center>().HasKey(c => new { c.idCenter, c.idOrganization, c.orgType, c.illness });
            modelBuilder.Entity<City>().HasKey(c => new { c.idCity, c.idProvince, c.idRegion, c.idCountry });
            modelBuilder.Entity<Country>().HasKey(c => new { c.idCountry });
            modelBuilder.Entity<EvaluatorTeam>().HasKey(t => new { t.idEvaluatorTeam, t.idEvaluatorOrganization, t.orgTypeEvaluator, t.idEvaluatedOrganization, t.orgTypeEvaluated, t.idCenter, t.illness});
            modelBuilder.Entity<Evidence>().HasKey(e => new { e.idEvidence, e.idAmbit, e.idIndicator, e.indicatorType, e.indicatorVersion });
            modelBuilder.Entity<Indicator>().HasKey(i => new { i.idAmbit, i.idIndicator,i.indicatorType,i.indicatorVersion});
            modelBuilder.Entity<IndicatorsEvaluation>().HasKey(e => new { e.evaluationDate, e.idEvaluatorTeam, e.idEvaluatorOrganization, e.orgTypeEvaluator, e.idEvaluatedOrganization, e.orgTypeEvaluated, e.illness });
            modelBuilder.Entity<IndicatorsEvaluationReg>().HasKey(r => new { r.evaluationDate,r.idEvaluatorTeam, r.idEvaluatorOrganization, r.orgTypeEvaluator,r.idEvaluatedOrganization,r.orgTypeEvaluated,r.illness,r.idCenter, r.idAmbit,r.idIndicator,r.idEvidence,r.indicatorVersion });
            modelBuilder.Entity<Organization>().HasKey(o => new { o.idOrganization,o.orgType,o.illness });
            modelBuilder.Entity<Province>().HasKey(p => new { p.idProvince,p.idRegion,p.idCountry });
            modelBuilder.Entity<Region>().HasKey(r => new { r.idRegion,r.idCountry });
            modelBuilder.Entity<Request>().HasKey(r => new { r.email });
            modelBuilder.Entity<Session>().HasKey(s => new { s.sessionToken });
            modelBuilder.Entity<User>().HasKey(u => new { u.emailUser });
        }


    }
}
