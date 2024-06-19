package gui.geo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cli.organization.data.geo.City;
import cli.organization.data.geo.Country;
import cli.organization.data.geo.Province;
import cli.organization.data.geo.Region;

public class GeoDatabase{
    private static final String DATABASE_NAME = "geo.db";
    private static final String DATABASE_PATH = "/data/data/com.fundacionmiradas.indicatorsevaluation/databases/";
    private Context context;
    private SQLiteDatabase database;

    public GeoDatabase(Context context) {
        this.context = context;
        copyDatabase();
        openDatabase();
    }

    private void copyDatabase() {
        try {
            File dbFile = new File(DATABASE_PATH + DATABASE_NAME);
            if (!dbFile.exists()) {
                InputStream inputStream = context.getAssets().open(DATABASE_NAME);
                String outFileName = DATABASE_PATH + DATABASE_NAME;
                File dir = new File(DATABASE_PATH);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                OutputStream outputStream = new FileOutputStream(outFileName);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }
                outputStream.flush();
                outputStream.close();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void openDatabase() {
        String path = DATABASE_PATH + DATABASE_NAME;
        database = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public SQLiteDatabase getDatabase() {
        return database;
    }

    public void closeDatabase() {
        if (database != null && database.isOpen()) {
            database.close();
        }
    }


    public List<Country> getCountries() {
        List<Country> countries = new ArrayList<>();
        String query="SELECT * FROM countries ORDER BY ";
        if (Locale.getDefault().getLanguage().equals("es")) {
            query+=" CASE idCountry\n" +
                    "        WHEN 'ESP' THEN 1\n" +
                    "        WHEN 'AND' THEN 2\n" +
                    "        WHEN 'USA' THEN 3\n" +
                    "        WHEN 'ARG' THEN 4\n" +
                    "        WHEN 'BOL' THEN 5\n" +
                    "        WHEN 'CHL' THEN 6\n" +
                    "        WHEN 'COL' THEN 7\n" +
                    "        WHEN 'CRI' THEN 8\n" +
                    "        WHEN 'CUB' THEN 9\n" +
                    "        WHEN 'ECU' THEN 10\n" +
                    "        WHEN 'SLV' THEN 11\n" +
                    "        WHEN 'GUA' THEN 12\n" +
                    "        WHEN 'GNQ' THEN 13\n" +
                    "        WHEN 'HND' THEN 14\n" +
                    "        WHEN 'MEX' THEN 15\n" +
                    "        WHEN 'NIC' THEN 16\n" +
                    "        WHEN 'PAN' THEN 17\n" +
                    "        WHEN 'PRY' THEN 18\n" +
                    "        WHEN 'PER' THEN 19\n" +
                    "        WHEN 'PRI' THEN 20\n" +
                    "        WHEN 'DOM' THEN 21\n" +
                    "        WHEN 'URY' THEN 22\n" +
                    "        WHEN 'VEN' THEN 23\n" +
                    "        ELSE 24\n" +
                    "    END,\n" +
                    "    nameSpanish;";
        } else if (Locale.getDefault().getLanguage().equals("fr")) {
            query+=" CASE idCountry\n" +
                    "        WHEN 'FRA' THEN 1\n" +
                    "        WHEN 'MCO' THEN 2\n" +
                    "        WHEN 'BEL' THEN 3\n" +
                    "        WHEN 'CHE' THEN 4\n" +
                    "        WHEN 'LUX' THEN 5\n" +
                    "        WHEN 'CAN' THEN 6\n" +
                    "        WHEN 'MAR' THEN 7\n" +
                    "        WHEN 'DZA' THEN 8\n" +
                    "        ELSE 9\n" +
                    "    END,\n" +
                    "    nameFrench;";
        } else if (Locale.getDefault().getLanguage().equals("eu")) {
            query+=" CASE idCountry\n" +
                    "        WHEN 'ESP' THEN 1\n" +
                    "        WHEN 'AND' THEN 2\n" +
                    "        WHEN 'USA' THEN 3\n" +
                    "        WHEN 'ARG' THEN 4\n" +
                    "        WHEN 'BOL' THEN 5\n" +
                    "        WHEN 'CHL' THEN 6\n" +
                    "        WHEN 'COL' THEN 7\n" +
                    "        WHEN 'CRI' THEN 8\n" +
                    "        WHEN 'CUB' THEN 9\n" +
                    "        WHEN 'ECU' THEN 10\n" +
                    "        WHEN 'SLV' THEN 11\n" +
                    "        WHEN 'GUA' THEN 12\n" +
                    "        WHEN 'GNQ' THEN 13\n" +
                    "        WHEN 'HND' THEN 14\n" +
                    "        WHEN 'MEX' THEN 15\n" +
                    "        WHEN 'NIC' THEN 16\n" +
                    "        WHEN 'PAN' THEN 17\n" +
                    "        WHEN 'PRY' THEN 18\n" +
                    "        WHEN 'PER' THEN 19\n" +
                    "        WHEN 'PRI' THEN 20\n" +
                    "        WHEN 'DOM' THEN 21\n" +
                    "        WHEN 'URY' THEN 22\n" +
                    "        WHEN 'VEN' THEN 23\n" +
                    "        ELSE 24\n" +
                    "    END,\n" +
                    "    nameBasque;";
        } else if (Locale.getDefault().getLanguage().equals("ca")) {
            query+=" CASE idCountry\n" +
                    "        WHEN 'ESP' THEN 1\n" +
                    "        WHEN 'AND' THEN 2\n" +
                    "        WHEN 'USA' THEN 3\n" +
                    "        WHEN 'ARG' THEN 4\n" +
                    "        WHEN 'BOL' THEN 5\n" +
                    "        WHEN 'CHL' THEN 6\n" +
                    "        WHEN 'COL' THEN 7\n" +
                    "        WHEN 'CRI' THEN 8\n" +
                    "        WHEN 'CUB' THEN 9\n" +
                    "        WHEN 'ECU' THEN 10\n" +
                    "        WHEN 'SLV' THEN 11\n" +
                    "        WHEN 'GUA' THEN 12\n" +
                    "        WHEN 'GNQ' THEN 13\n" +
                    "        WHEN 'HND' THEN 14\n" +
                    "        WHEN 'MEX' THEN 15\n" +
                    "        WHEN 'NIC' THEN 16\n" +
                    "        WHEN 'PAN' THEN 17\n" +
                    "        WHEN 'PRY' THEN 18\n" +
                    "        WHEN 'PER' THEN 19\n" +
                    "        WHEN 'PRI' THEN 20\n" +
                    "        WHEN 'DOM' THEN 21\n" +
                    "        WHEN 'URY' THEN 22\n" +
                    "        WHEN 'VEN' THEN 23\n" +
                    "        ELSE 24\n" +
                    "    END,\n" +
                    "    nameCatalan;";
        } else if (Locale.getDefault().getLanguage().equals("nl")) {
            query+=" CASE idCountry\n" +
                    "        WHEN 'NLD' THEN 1\n" +
                    "        WHEN 'BEL' THEN 2\n" +
                    "        WHEN 'FRA' THEN 3\n" +
                    "        WHEN 'ABW' THEN 4\n" +
                    "        WHEN 'SUR' THEN 5\n" +
                    "        ELSE 6\n" +
                    "    END,\n" +
                    "    nameDutch;";
        } else if (Locale.getDefault().getLanguage().equals("gl")) {
            query+=" CASE idCountry\n" +
                    "        WHEN 'ESP' THEN 1\n" +
                    "        WHEN 'AND' THEN 2\n" +
                    "        WHEN 'USA' THEN 3\n" +
                    "        WHEN 'ARG' THEN 4\n" +
                    "        WHEN 'BOL' THEN 5\n" +
                    "        WHEN 'CHL' THEN 6\n" +
                    "        WHEN 'COL' THEN 7\n" +
                    "        WHEN 'CRI' THEN 8\n" +
                    "        WHEN 'CUB' THEN 9\n" +
                    "        WHEN 'ECU' THEN 10\n" +
                    "        WHEN 'SLV' THEN 11\n" +
                    "        WHEN 'GUA' THEN 12\n" +
                    "        WHEN 'GNQ' THEN 13\n" +
                    "        WHEN 'HND' THEN 14\n" +
                    "        WHEN 'MEX' THEN 15\n" +
                    "        WHEN 'NIC' THEN 16\n" +
                    "        WHEN 'PAN' THEN 17\n" +
                    "        WHEN 'PRY' THEN 18\n" +
                    "        WHEN 'PER' THEN 19\n" +
                    "        WHEN 'PRI' THEN 20\n" +
                    "        WHEN 'DOM' THEN 21\n" +
                    "        WHEN 'URY' THEN 22\n" +
                    "        WHEN 'VEN' THEN 23\n" +
                    "        ELSE 24\n" +
                    "    END,\n" +
                    "    nameGalician;";
        } else if (Locale.getDefault().getLanguage().equals("de")) {
            query+=" CASE idCountry\n" +
                    "        WHEN 'DEU' THEN 1\n" +
                    "        WHEN 'AUT' THEN 2\n" +
                    "        WHEN 'BEL' THEN 3\n" +
                    "        WHEN 'LUX' THEN 4\n" +
                    "        WHEN 'LIE' THEN 5\n" +
                    "        WHEN 'CHE' THEN 6\n" +
                    "        ELSE 7\n" +
                    "    END,\n" +
                    "    nameGerman;";
        } else if (Locale.getDefault().getLanguage().equals("it")) {
            query+=" CASE idCountry\n" +
                    "        WHEN 'ITA' THEN 1\n" +
                    "        WHEN 'CHE' THEN 2\n" +
                    "        WHEN 'SMR' THEN 3\n" +
                    "        WHEN 'VAT' THEN 4\n" +
                    "        ELSE 5\n" +
                    "    END,\n" +
                    "    nameItalian;";
        } else if (Locale.getDefault().getLanguage().equals("pt")) {
            query+=" CASE idCountry\n" +
                    "        WHEN 'PRT' THEN 1\n" +
                    "        WHEN 'BRA' THEN 2\n" +
                    "        WHEN 'GNB' THEN 3\n" +
                    "        WHEN 'MOZ' THEN 4\n" +
                    "        WHEN 'STP' THEN 5\n" +
                    "        WHEN 'TLS' THEN 6\n" +
                    "        ELSE 7\n" +
                    "    END,\n" +
                    "    namePortuguese;";
        } else {
            query+=" CASE idCountry\n" +
                    "        WHEN 'USA' THEN 1\n" +
                    "        WHEN 'GBR' THEN 2\n" +
                    "        WHEN 'AUS' THEN 3\n" +
                    "        WHEN 'CAN' THEN 4\n" +
                    "        WHEN 'GIB' THEN 5\n" +
                    "        WHEN 'IOT' THEN 6\n" +
                    "        WHEN 'IND' THEN 7\n" +
                    "        WHEN 'IRL' THEN 8\n" +
                    "        WHEN 'NZF' THEN 9\n" +
                    "        WHEN 'ZAF' THEN 10\n" +
                    "        ELSE 11\n" +
                    "    END,\n" +
                    "    nameEnglish;";
        }
        Cursor cursor=database.rawQuery(query,null);
        if (cursor.moveToFirst()) {
            do {
                countries.add(new Country(cursor.getString(0),cursor.getString(1),cursor.getString(2),
                        cursor.getString(3),cursor.getString(4),cursor.getString(5),
                        cursor.getString(6),cursor.getString(7),cursor.getString(8),
                        cursor.getString(9),cursor.getString(10),cursor.getString(11),
                        cursor.getString(12)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return countries;
    }

    public List<Region> getRegionsByCountry(String idCountry){
        List<Region> regions=new ArrayList<>();
        String query="SELECT * FROM regions WHERE idCountry=? ORDER BY ";
        if (Locale.getDefault().getLanguage().equals("es")) {
            query+=" nameSpanish;";
        } else if (Locale.getDefault().getLanguage().equals("fr")) {
            query+=" nameFrench;";
        } else if (Locale.getDefault().getLanguage().equals("eu")) {
            query+=" nameBasque;";
        } else if (Locale.getDefault().getLanguage().equals("ca")) {
            query+=" nameCatalan;";
        } else if (Locale.getDefault().getLanguage().equals("nl")) {
            query+=" nameDutch;";
        } else if (Locale.getDefault().getLanguage().equals("gl")) {
            query+=" nameGalician;";
        } else if (Locale.getDefault().getLanguage().equals("de")) {
            query+=" nameGerman;";
        } else if (Locale.getDefault().getLanguage().equals("it")) {
            query+=" nameItalian;";
        } else if (Locale.getDefault().getLanguage().equals("pt")) {
            query+=" namePortuguese;";
        } else {
            query+=" nameEnglish;";
        }
        Cursor cursor=database.rawQuery(query,new String[]{idCountry});
        if (cursor.moveToFirst()) {
            do {
                regions.add(new Region(cursor.getInt(0),cursor.getString(1),cursor.getString(2),
                        cursor.getString(3),cursor.getString(4),cursor.getString(5),
                        cursor.getString(6),cursor.getString(7),cursor.getString(8),
                        cursor.getString(9),cursor.getString(10),cursor.getString(11)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return regions;
    }

    public List<Province> getProvincesByRegion(int idRegion,String idCountry){
        List<Province> provinces=new ArrayList<>();
        String query="SELECT * FROM provinces WHERE idRegion=? AND idCountry=? ORDER BY ";
        if (Locale.getDefault().getLanguage().equals("es")) {
            query+=" nameSpanish;";
        } else if (Locale.getDefault().getLanguage().equals("fr")) {
            query+=" nameFrench;";
        } else if (Locale.getDefault().getLanguage().equals("eu")) {
            query+=" nameBasque;";
        } else if (Locale.getDefault().getLanguage().equals("ca")) {
            query+=" nameCatalan;";
        } else if (Locale.getDefault().getLanguage().equals("nl")) {
            query+=" nameDutch;";
        } else if (Locale.getDefault().getLanguage().equals("gl")) {
            query+=" nameGalician;";
        } else if (Locale.getDefault().getLanguage().equals("de")) {
            query+=" nameGerman;";
        } else if (Locale.getDefault().getLanguage().equals("it")) {
            query+=" nameItalian;";
        } else if (Locale.getDefault().getLanguage().equals("pt")) {
            query+=" namePortuguese;";
        } else {
            query+=" nameEnglish;";
        }
        Cursor cursor=database.rawQuery(query,new String[]{""+idRegion,idCountry});
        if (cursor.moveToFirst()) {
            do {
                provinces.add(new Province(cursor.getInt(0),cursor.getInt(1),cursor.getString(2),
                        cursor.getString(3),cursor.getString(4),cursor.getString(5),
                        cursor.getString(6),cursor.getString(7),cursor.getString(8),
                        cursor.getString(9),cursor.getString(10),cursor.getString(11),cursor.getString(12)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return provinces;
    }

    public List<City> getCitiesByProvince(int idProvince, int idRegion, String idCountry){
        List<City> cities=new ArrayList<>();
        String query="SELECT * FROM cities WHERE idProvince=? AND idRegion=? AND idCountry=? ORDER BY ";
        if (Locale.getDefault().getLanguage().equals("es")) {
            query+=" nameSpanish;";
        } else if (Locale.getDefault().getLanguage().equals("fr")) {
            query+=" nameFrench;";
        } else if (Locale.getDefault().getLanguage().equals("eu")) {
            query+=" nameBasque;";
        } else if (Locale.getDefault().getLanguage().equals("ca")) {
            query+=" nameCatalan;";
        } else if (Locale.getDefault().getLanguage().equals("nl")) {
            query+=" nameDutch;";
        } else if (Locale.getDefault().getLanguage().equals("gl")) {
            query+=" nameGalician;";
        } else if (Locale.getDefault().getLanguage().equals("de")) {
            query+=" nameGerman;";
        } else if (Locale.getDefault().getLanguage().equals("it")) {
            query+=" nameItalian;";
        } else if (Locale.getDefault().getLanguage().equals("pt")) {
            query+=" namePortuguese;";
        } else {
            query+=" nameEnglish;";
        }
        Cursor cursor=database.rawQuery(query,new String[]{""+idProvince,""+idRegion,idCountry});
        if (cursor.moveToFirst()) {
            do {
                cities.add(new City(cursor.getInt(0),cursor.getInt(1),cursor.getInt(2),
                        cursor.getString(3),cursor.getString(4),cursor.getString(5),
                        cursor.getString(6),cursor.getString(7),cursor.getString(8),
                        cursor.getString(9),cursor.getString(10),cursor.getString(11),
                        cursor.getString(12),cursor.getString(13)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return cities;
    }

}
