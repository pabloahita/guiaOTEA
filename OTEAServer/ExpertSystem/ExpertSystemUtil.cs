using System;
using System.Collections.Generic;
using NRules;
using NRules.Fluent;

public class ExpertSystemUtil
{
    private static readonly Lazy<ExpertSystemUtil> instance = new Lazy<ExpertSystemUtil>(() => new ExpertSystemUtil());

    private ExpertSystemUtil() { }

    public static ExpertSystemUtil Instance => instance.Value;

    public NRules.ISession CreateSessionWithRules(params Type[] ruleTypes)
    {
        var repository = new RuleRepository();
        LoadRules(repository, ruleTypes);
        var sessionFactory = CompileRules(repository);
        return sessionFactory.CreateSession();
    }

    private void LoadRules(RuleRepository repository, params Type[] ruleTypes)
    {
        foreach (var ruleType in ruleTypes)
        {
            repository.Load(x => x.From(ruleType.Assembly));
        }
    }

    private ISessionFactory CompileRules(RuleRepository repository)
    {
        return repository.Compile();
    }

    public void RunRules(NRules.ISession session, IEnumerable<object> facts)
    {
        session.InsertAll(facts);
        session.Fire();
        session.RetractAll(facts);
    }
}
