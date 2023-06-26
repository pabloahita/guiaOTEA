using OTEAServer.Services;

var builder = WebApplication.CreateBuilder(args); // Web app builder

// Add services to the container.
builder.Services.AddRazorPages();
// Add custom service to the container builder.Service.AddScoped<ServiceName>();
builder.Services.AddScoped<UsersService>();
builder.Services.AddScoped<OrganizationsService>();
builder.Services.AddScoped<AddressesService>();
builder.Services.AddScoped<IndicatorsService>();
builder.Services.AddScoped<EvidencesService>();
builder.Services.AddScoped<CitiesService>();
builder.Services.AddScoped<ProvincesService>();
builder.Services.AddScoped<RegionsService>();
builder.Services.AddScoped<CountriesService>();
builder.Services.AddScoped<IndicatorsEvaluationsService>();
builder.Services.AddScoped<CentersService>();
builder.Services.AddScoped<IndicatorsEvaluationRegsService>();

var app = builder.Build();

// Configure the HTTP request pipeline.
if (!app.Environment.IsDevelopment())
{
    app.UseExceptionHandler("/Error");
    // The default HSTS value is 30 days. You may want to change this for production scenarios, see https://aka.ms/aspnetcore-hsts.
    app.UseHsts();
}

app.UseHttpsRedirection();
app.UseStaticFiles();

app.UseRouting();

app.UseEndpoints(endpoints =>
{
    endpoints.MapControllers();
});

app.UseAuthorization();

app.MapRazorPages();

app.Run();
