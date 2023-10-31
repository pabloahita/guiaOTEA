package cli.organization.data.geo;

import com.google.gson.annotations.SerializedName;
import com.vdurmont.emoji.EmojiParser;

import java.io.Serializable;

public class Country implements Serializable {

    @SerializedName("nameSpanish")
    public String nameSpanish;

    @SerializedName("nameEnglish")
    public String nameEnglish;

    @SerializedName("nameFrench")
    public String nameFrench;

    @SerializedName("nameBasque")
    public String nameBasque;

    @SerializedName("nameCatalan")
    public String nameCatalan;

    @SerializedName("nameDutch")
    public String nameDutch;

    @SerializedName("nameGalician")
    public String nameGalician;

    @SerializedName("nameGerman")
    public String nameGerman;

    @SerializedName("nameItalian")
    public String nameItalian;

    @SerializedName("namePortuguese")
    public String namePortuguese;

    @SerializedName("idCountry")
    public String idCountry;

    @SerializedName("phone_code")
    public String phone_code;

    @SerializedName("flag")
    public String flag;

    public Country(String idCountry, String nameSpanish, String nameEnglish, String nameFrench, String nameBasque, String nameCatalan, String nameDutch, String nameGalician, String nameGerman, String nameItalian, String namePortuguese, String phone_code, String flag){
        setIdCountry(idCountry);
        setNameSpanish(nameSpanish);
        setNameEnglish(nameEnglish);
        setNameFrench(nameFrench);
        setNameBasque(nameBasque);
        setNameCatalan(nameCatalan);
        setNameDutch(nameDutch);
        setNameGalician(nameGalician);
        setNameGerman(nameGerman);
        setNameItalian(nameItalian);
        setNamePortuguese(namePortuguese);
        setPhone_code(phone_code);
        setFlag(EmojiParser.parseToUnicode(flag)); //It's mandatory to convert it to a visible emoji because its code is reached by server via JSON
    }

    public String getNameSpanish() {
        return nameSpanish;
    }

    public void setNameSpanish(String nameSpanish) {
        this.nameSpanish = nameSpanish;
    }

    public String getNameEnglish() {
        return nameEnglish;
    }

    public void setNameEnglish(String nameEnglish) {
        this.nameEnglish = nameEnglish;
    }

    public String getNameFrench() {
        return nameFrench;
    }

    public void setNameFrench(String nameFrench) {
        this.nameFrench = nameFrench;
    }

    public String getNameBasque() {
        return nameBasque;
    }

    public void setNameBasque(String nameBasque) {
        this.nameBasque = nameBasque;
    }

    public String getNameCatalan() {
        return nameCatalan;
    }

    public void setNameCatalan(String nameCatalan) {
        this.nameCatalan = nameCatalan;
    }

    public String getNameDutch() {
        return nameDutch;
    }

    public void setNameDutch(String nameDutch) {
        this.nameDutch = nameDutch;
    }

    public String getNameGalician() {
        return nameGalician;
    }

    public void setNameGalician(String nameGalician) {
        this.nameGalician = nameGalician;
    }

    public String getNameGerman() {
        return nameGerman;
    }

    public void setNameGerman(String nameGerman) {
        this.nameGerman = nameGerman;
    }

    public String getNameItalian() {
        return nameItalian;
    }

    public void setNameItalian(String nameItalian) {
        this.nameItalian = nameItalian;
    }

    public String getNamePortuguese() {
        return namePortuguese;
    }

    public void setNamePortuguese(String namePortuguese) {
        this.namePortuguese = namePortuguese;
    }

    public String getIdCountry() {
        return idCountry;
    }

    public void setIdCountry(String idCountry) {
        this.idCountry = idCountry;
    }

    public String getPhone_code() {
        return phone_code;
    }

    public void setPhone_code(String phone_code) {
        this.phone_code = phone_code;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
