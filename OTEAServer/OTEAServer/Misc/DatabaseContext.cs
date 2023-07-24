using Microsoft.EntityFrameworkCore;
using OTEAServer.Models;

namespace OTEAServer.Misc
{
    public class DatabaseContext : DbContext 
    {
           public DatabaseContext(DbContextOptions<DatabaseContext> options) :base(options) { }



        public DbSet<Address> Addresses { get; set; }

        public DbSet<Center> Centers { get; set; }

        public DbSet<City> Cities { get; set; }

        public DbSet<Country> Countries { get; set; }

        public DbSet<EvaluatorTeam> EvaluatorTeams { get; set; }

        public DbSet<EvaluatorTeamMember> EvaluatorTeamMembers { get; set; }

        public DbSet<Evidence> Evidences { get; set; }

        public DbSet<Indicator> Indicators { get; set; }

        public DbSet<IndicatorsEvaluation> IndicatorsEvaluations { get; set; }

        public DbSet<IndicatorsEvaluationReg> IndicatorsEvaluationRegs { get; set; }

        public DbSet<Organization> Organizations { get; set; }

        public DbSet<Province> Provinces { get; set; }

        public DbSet<Region> Regions { get; set; }

        public DbSet<User> Users { get; set; }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<Address>().HasKey(a => new { a.idAddress });
            modelBuilder.Entity<Center>().HasKey(c => new { c.idCenter, c.idOrganization, c.orgType, c.illness });
            modelBuilder.Entity<City>().HasKey(c => new { c.idCity, c.idProvince, c.idRegion, c.idCountry });
            modelBuilder.Entity<Country>().HasKey(c => new { c.idCountry });
            modelBuilder.Entity<EvaluatorTeam>().HasKey(t => new { t.idEvaluatorTeam, t.idOrganization, t.orgType, t.illness});
            modelBuilder.Entity<EvaluatorTeamMember>().HasKey(m => new { m.emailUser, m.idEvaluatorTeam, m.idEvaluatorOrganization, m.orgType, m.illness});
            modelBuilder.Entity<Evidence>().HasKey(e => new { e.idEvidence, e.idIndicator, e.indicatorType, e.indicatorVersion });
            modelBuilder.Entity<Indicator>().HasKey(i => new { i.indicatorId,i.indicatorType,i.indicatorVersion});
            modelBuilder.Entity<IndicatorsEvaluation>().HasKey(e => new { e.evaluationDate, e.idEvaluatedOrganization, e.orgTypeEvaluated, e.illness });
            modelBuilder.Entity<IndicatorsEvaluationReg>().HasKey(r => new { r.evaluationDate,r.idEvaluatedOrganization,r.orgTypeEvaluated,r.illness,r.indicatorId,r.idEvidence,r.indicatorVersion });
            modelBuilder.Entity<Organization>().HasKey(o => new { o.IdOrganization,o.orgType,o.illness });
            modelBuilder.Entity<Province>().HasKey(p => new { p.idProvince,p.idRegion,p.idCountry });
            modelBuilder.Entity<Region>().HasKey(r => new { r.idRegion,r.idCountry });
            modelBuilder.Entity<User>().HasKey(u => new { u.emailUser });
        }


    }
}
