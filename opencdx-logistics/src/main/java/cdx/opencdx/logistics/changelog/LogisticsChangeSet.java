/*
 * Copyright 2024 Safe Health Systems, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cdx.opencdx.logistics.changelog;

import cdx.opencdx.commons.annotations.ExcludeFromJacocoGeneratedReport;
import cdx.opencdx.commons.model.OpenCDXCountryModel;
import cdx.opencdx.commons.repository.OpenCDXCountryRepository;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.decorator.impl.MongockTemplate;
import com.mongodb.client.model.Indexes;
import java.util.List;

/**
 * Change sets to MongoDB for Connected Test
 */
@ChangeLog(order = "001")
@ExcludeFromJacocoGeneratedReport
public class LogisticsChangeSet {

    private static final String USER_ID = "userId";
    private static final String DEVICES = "devices";
    private static final String TESTCASES = "testcases";
    private static final String SYSTEM = "SYSTEM";
    private static final String EUROPE = "EUROPE";
    private static final String AFRICA = "AFRICA";
    private static final String OCEANIA = "OCEANIA";
    private static final String NORTH_AMERICA = "NORTH_AMERICA";
    private static final String SOUTH_AMERICA = "SOUTH_AMERICA";
    public static final String ASIA = "ASIA";

    /**
     * Default Constructor
     */
    public LogisticsChangeSet() {
        // Explicit declaration to prevent this class from inadvertently being made instantiable
    }

    /**
     * Create an index based on the user id
     * @param mongockTemplate MongockTemplate to modify MongoDB.
     * @param openCDXCurrentUser Current User to use for authentication.
     */
    @ChangeSet(order = "001", id = "Setup Logistics4 Index", author = "Gaurav Mishra")
    public void setupConnectedTestIndex(MongockTemplate mongockTemplate, OpenCDXCurrentUser openCDXCurrentUser) {
        openCDXCurrentUser.configureAuthentication(SYSTEM);
        mongockTemplate.getCollection(DEVICES).createIndex(Indexes.ascending(List.of(USER_ID)));
        mongockTemplate.getCollection(DEVICES).createIndex(Indexes.ascending(List.of("manufacturerCountryId")));
        mongockTemplate.getCollection(DEVICES).createIndex(Indexes.ascending(List.of("vendorCountryId")));
        mongockTemplate.getCollection(DEVICES).createIndex(Indexes.ascending(List.of("vendorId")));
        mongockTemplate.getCollection(DEVICES).createIndex(Indexes.ascending(List.of("manufacturerId")));
        mongockTemplate.getCollection("manufacturer").createIndex(Indexes.ascending(List.of("countryId")));
        mongockTemplate.getCollection(TESTCASES).createIndex(Indexes.ascending(List.of("manufacturerId")));
        mongockTemplate.getCollection(TESTCASES).createIndex(Indexes.ascending(List.of("vendorId")));
        mongockTemplate.getCollection("vendor").createIndex(Indexes.ascending(List.of("countryId")));
        mongockTemplate.getCollection("shipping").createIndex(Indexes.ascending(List.of("trackingNumber")));
        mongockTemplate.getCollection("order").createIndex(Indexes.ascending(List.of("patientId")));
    }

    /**
     * Create country in db
     * @param openCDXCountryRepository to modify MongoDB.
     * @param openCDXCurrentUser Current User to use for authentication.
     */
    @ChangeSet(order = "002", id = "Setup Countries", author = "Gaurav Mishra")
    public void setupCountriesIndex(
            OpenCDXCountryRepository openCDXCountryRepository, OpenCDXCurrentUser openCDXCurrentUser) {
        openCDXCurrentUser.configureAuthentication(SYSTEM);
        openCDXCountryRepository.saveAll(List.of(
                new OpenCDXCountryModel(
                        "Afghanistan", "AF", "AFG", "af", "AF", 4, "93", ASIA, List.of("fa-AF", "ps", "uz-AF", "tk")),
                new OpenCDXCountryModel("Albania", "AL", "ALB", "al", "AL", 8, "355", EUROPE, List.of("sq", "el")),
                new OpenCDXCountryModel("Algeria", "DZ", "DZA", "dz", "AG", 12, "213", AFRICA, List.of("ar-DZ")),
                new OpenCDXCountryModel(
                        "American Samoa", "AS", "ASM", "as", "AQ", 16, "1-684", OCEANIA, List.of("en-AS", "sm", "to")),
                new OpenCDXCountryModel("Andorra", "AD", "AND", "ad", "AN", 20, "376", EUROPE, List.of("ca")),
                new OpenCDXCountryModel("Angola", "AO", "AGO", "ao", "AO", 24, "244", AFRICA, List.of("pt-AO")),
                new OpenCDXCountryModel(
                        "Anguilla", "AI", "AIA", "ai", "AV", 660, "1-264", NORTH_AMERICA, List.of("en-AI")),
                new OpenCDXCountryModel("Antarctica", "AQ", "ATA", "aq", "AY", 10, "672", "ANTARCTICA", List.of()),
                new OpenCDXCountryModel(
                        "Antigua and Barbuda", "AG", "ATG", "ag", "AC", 28, "1-268", NORTH_AMERICA, List.of("en-AG")),
                new OpenCDXCountryModel(
                        "Argentina",
                        "AR",
                        "ARG",
                        "ar",
                        "AR",
                        32,
                        "54",
                        SOUTH_AMERICA,
                        List.of("es-AR", "en", "it", "de", "fr", "gn")),
                new OpenCDXCountryModel("Armenia", "AM", "ARM", "am", "AM", 51, "374", ASIA, List.of("hy")),
                new OpenCDXCountryModel(
                        "Aruba", "AW", "ABW", "aw", "AA", 533, "297", NORTH_AMERICA, List.of("nl-AW", "es", "en")),
                new OpenCDXCountryModel("Australia", "AU", "AUS", "au", "AS", 36, "61", OCEANIA, List.of("en-AU")),
                new OpenCDXCountryModel(
                        "Austria", "AT", "AUT", "at", "AU", 40, "43", EUROPE, List.of("de-AT", "hr", "hu", "sl")),
                new OpenCDXCountryModel(
                        "Azerbaijan", "AZ", "AZE", "az", "AJ", 31, "994", ASIA, List.of("az", "ru", "hy")),
                new OpenCDXCountryModel(
                        "Bahamas", "BS", "BHS", "bs", "BF", 44, "1-242", NORTH_AMERICA, List.of("en-BS")),
                new OpenCDXCountryModel(
                        "Bahrain", "BH", "BHR", "bh", "BA", 48, "973", ASIA, List.of("ar-BH", "en", "fa", "ur")),
                new OpenCDXCountryModel(
                        "Bangladesh", "BD", "BGD", "bd", "BG", 50, "880", ASIA, List.of("bn-BD", "en")),
                new OpenCDXCountryModel(
                        "Barbados", "BB", "BRB", "bb", "BB", 52, "1-246", NORTH_AMERICA, List.of("en-BB")),
                new OpenCDXCountryModel("Belarus", "BY", "BLR", "by", "BO", 112, "375", EUROPE, List.of("be", "ru")),
                new OpenCDXCountryModel(
                        "Belgium", "BE", "BEL", "be", "BE", 56, "32", EUROPE, List.of("nl-BE", "fr-BE", "de-BE")),
                new OpenCDXCountryModel(
                        "Belize", "BZ", "BLZ", "bz", "BH", 84, "501", NORTH_AMERICA, List.of("en-BZ", "es")),
                new OpenCDXCountryModel("Benin", "BJ", "BEN", "bj", "BN", 204, "229", AFRICA, List.of("fr-BJ")),
                new OpenCDXCountryModel(
                        "Bermuda", "BM", "BMU", "bm", "BD", 60, "1-441", NORTH_AMERICA, List.of("en-BM", "pt")),
                new OpenCDXCountryModel("Bhutan", "BT", "BTN", "bt", "BT", 64, "975", ASIA, List.of("dz")),
                new OpenCDXCountryModel(
                        "Bolivia", "BO", "BOL", "bo", "BL", 68, "591", SOUTH_AMERICA, List.of("es-BO", "qu", "ay")),
                new OpenCDXCountryModel(
                        "Bosnia and Herzegovina",
                        "BA",
                        "BIH",
                        "ba",
                        "BK",
                        70,
                        "387",
                        EUROPE,
                        List.of("bs", "hr-BA", "sr-BA")),
                new OpenCDXCountryModel(
                        "Botswana", "BW", "BWA", "bw", "BC", 72, "267", AFRICA, List.of("en-BW", "tn-BW")),
                new OpenCDXCountryModel(
                        "Brazil", "BR", "BRA", "br", "BR", 76, "55", SOUTH_AMERICA, List.of("pt-BR", "es", "en", "fr")),
                new OpenCDXCountryModel(
                        "British Indian Ocean Territory", "IO", "IOT", "io", "IO", 86, "246", ASIA, List.of("en-IO")),
                new OpenCDXCountryModel(
                        "British Virgin Islands",
                        "VG",
                        "VGB",
                        "vg",
                        "VI",
                        92,
                        "1-284",
                        NORTH_AMERICA,
                        List.of("en-VG")),
                new OpenCDXCountryModel(
                        "Brunei", "BN", "BRN", "bn", "BX", 96, "673", ASIA, List.of("ms-BN", "en-BN")),
                new OpenCDXCountryModel(
                        "Bulgaria", "BG", "BGR", "bg", "BU", 100, "359", EUROPE, List.of("bg", "tr-BG")),
                new OpenCDXCountryModel("Burkina Faso", "BF", "BFA", "bf", "UV", 854, "226", AFRICA, List.of("fr-BF")),
                new OpenCDXCountryModel("Burundi", "BI", "BDI", "bi", "BY", 108, "257", AFRICA, List.of("fr-BI", "rn")),
                new OpenCDXCountryModel(
                        "Cambodia", "KH", "KHM", "kh", "CB", 116, "855", ASIA, List.of("km", "fr", "en")),
                new OpenCDXCountryModel(
                        "Cameroon", "CM", "CMR", "cm", "CM", 120, "237", AFRICA, List.of("en-CM", "fr-CM")),
                new OpenCDXCountryModel(
                        "Canada", "CA", "CAN", "ca", "CA", 124, "1", NORTH_AMERICA, List.of("en-CA", "fr-CA", "iu")),
                new OpenCDXCountryModel("Cape Verde", "CV", "CPV", "cv", "CV", 132, "238", AFRICA, List.of("pt-CV")),
                new OpenCDXCountryModel(
                        "Cayman Islands", "KY", "CYM", "ky", "CJ", 136, "1-345", NORTH_AMERICA, List.of("en-KY")),
                new OpenCDXCountryModel(
                        "Central African Republic",
                        "CF",
                        "CAF",
                        "cf",
                        "CT",
                        140,
                        "236",
                        AFRICA,
                        List.of("fr-CF", "sg", "ln", "kg")),
                new OpenCDXCountryModel(
                        "Chad", "TD", "TCD", "td", "CD", 148, "235", AFRICA, List.of("fr-TD", "ar-TD", "sre")),
                new OpenCDXCountryModel("Chile", "CL", "CHL", "cl", "CI", 152, "56", SOUTH_AMERICA, List.of("es-CL")),
                new OpenCDXCountryModel(
                        "China",
                        "CN",
                        "CHN",
                        "cn",
                        "CH",
                        156,
                        "86",
                        ASIA,
                        List.of("zh-CN", "yue", "wuu", "dta", "ug", "za")),
                new OpenCDXCountryModel(
                        "Christmas Island", "CX", "CXR", "cx", "KT", 162, "61", ASIA, List.of("en", "zh", "ms-CC")),
                new OpenCDXCountryModel(
                        "Cocos Islands", "CC", "CCK", "cc", "CK", 166, "61", ASIA, List.of("ms-CC", "en")),
                new OpenCDXCountryModel(
                        "Colombia", "CO", "COL", "co", "CO", 170, "57", SOUTH_AMERICA, List.of("es-CO")),
                new OpenCDXCountryModel("Comoros", "KM", "COM", "km", "CN", 174, "269", AFRICA, List.of("ar", "fr-KM")),
                new OpenCDXCountryModel(
                        "Cook Islands", "CK", "COK", "ck", "CW", 184, "682", OCEANIA, List.of("en-CK", "mi")),
                new OpenCDXCountryModel(
                        "Costa Rica", "CR", "CRI", "cr", "CS", 188, "506", NORTH_AMERICA, List.of("es-CR", "en")),
                new OpenCDXCountryModel("Croatia", "HR", "HRV", "hr", "HR", 191, "385", EUROPE, List.of("hr-HR", "sr")),
                new OpenCDXCountryModel("Cuba", "CU", "CUB", "cu", "CU", 192, "53", NORTH_AMERICA, List.of("es-CU")),
                new OpenCDXCountryModel(
                        "Curacao", "CW", "CUW", "cw", "UC", 531, "599", NORTH_AMERICA, List.of("nl", "pap")),
                new OpenCDXCountryModel(
                        "Cyprus", "CY", "CYP", "cy", "CY", 196, "357", EUROPE, List.of("el-CY", "tr-CY", "en")),
                new OpenCDXCountryModel(
                        "Czech Republic", "CZ", "CZE", "cz", "EZ", 203, "420", EUROPE, List.of("cs", "sk")),
                new OpenCDXCountryModel(
                        "Democratic Republic of the Congo",
                        "CD",
                        "COD",
                        "cd",
                        "CG",
                        180,
                        "243",
                        AFRICA,
                        List.of("fr-CD", "ln", "kg")),
                new OpenCDXCountryModel(
                        "Denmark", "DK", "DNK", "dk", "DA", 208, "45", EUROPE, List.of("da-DK", "en", "fo", "de-DK")),
                new OpenCDXCountryModel(
                        "Djibouti", "DJ", "DJI", "dj", "DJ", 262, "253", AFRICA, List.of("fr-DJ", "ar", "so-DJ", "aa")),
                new OpenCDXCountryModel(
                        "Dominica", "DM", "DMA", "dm", "DO", 212, "1-767", NORTH_AMERICA, List.of("en-DM")),
                new OpenCDXCountryModel(
                        "Dominican Republic",
                        "DO",
                        "DOM",
                        "do",
                        "DR",
                        214,
                        "1-809, 1-829, 1-849",
                        NORTH_AMERICA,
                        List.of("es-DO")),
                new OpenCDXCountryModel(
                        "East Timor",
                        "TL",
                        "TLS",
                        "tl",
                        "TT",
                        626,
                        "670",
                        OCEANIA,
                        List.of("tet", "pt-TL", "id", "en")),
                new OpenCDXCountryModel(
                        "Ecuador", "EC", "ECU", "ec", "EC", 218, "593", SOUTH_AMERICA, List.of("es-EC")),
                new OpenCDXCountryModel(
                        "Egypt", "EG", "EGY", "eg", "EG", 818, "20", AFRICA, List.of("ar-EG", "en", "fr")),
                new OpenCDXCountryModel(
                        "El Salvador", "SV", "SLV", "sv", "ES", 222, "503", NORTH_AMERICA, List.of("es-SV")),
                new OpenCDXCountryModel(
                        "Equatorial Guinea", "GQ", "GNQ", "gq", "EK", 226, "240", AFRICA, List.of("es-GQ", "fr")),
                new OpenCDXCountryModel(
                        "Eritrea",
                        "ER",
                        "ERI",
                        "er",
                        "ER",
                        232,
                        "291",
                        AFRICA,
                        List.of("aa-ER", "ar", "tig", "kun", "ti-ER")),
                new OpenCDXCountryModel("Estonia", "EE", "EST", "ee", "EN", 233, "372", EUROPE, List.of("et", "ru")),
                new OpenCDXCountryModel(
                        "Ethiopia",
                        "ET",
                        "ETH",
                        "et",
                        "ET",
                        231,
                        "251",
                        AFRICA,
                        List.of("am", "en-ET", "om-ET", "ti-ET", "so-ET", "sid")),
                new OpenCDXCountryModel(
                        "Falkland Islands", "FK", "FLK", "fk", "FK", 238, "500", SOUTH_AMERICA, List.of("en-FK")),
                new OpenCDXCountryModel(
                        "Faroe Islands", "FO", "FRO", "fo", "FO", 234, "298", EUROPE, List.of("fo", "da-FO")),
                new OpenCDXCountryModel("Fiji", "FJ", "FJI", "fj", "FJ", 242, "679", OCEANIA, List.of("en-FJ", "fj")),
                new OpenCDXCountryModel(
                        "Finland", "FI", "FIN", "fi", "FI", 246, "358", EUROPE, List.of("fi-FI", "sv-FI", "smn")),
                new OpenCDXCountryModel(
                        "France",
                        "FR",
                        "FRA",
                        "fr",
                        "FR",
                        250,
                        "33",
                        EUROPE,
                        List.of("fr-FR", "frp", "br", "co", "ca", "eu", "oc")),
                new OpenCDXCountryModel(
                        "French Polynesia", "PF", "PYF", "pf", "FP", 258, "689", OCEANIA, List.of("fr-PF", "ty")),
                new OpenCDXCountryModel("Gabon", "GA", "GAB", "ga", "GB", 266, "241", AFRICA, List.of("fr-GA")),
                new OpenCDXCountryModel(
                        "Gambia",
                        "GM",
                        "GMB",
                        "gm",
                        "GA",
                        270,
                        "220",
                        AFRICA,
                        List.of("en-GM", "mnk", "wof", "wo", "ff")),
                new OpenCDXCountryModel(
                        "Georgia", "GE", "GEO", "ge", "GG", 268, "995", ASIA, List.of("ka", "ru", "hy", "az")),
                new OpenCDXCountryModel("Germany", "DE", "DEU", "de", "GM", 276, "49", EUROPE, List.of("de")),
                new OpenCDXCountryModel(
                        "Ghana", "GH", "GHA", "gh", "GH", 288, "233", AFRICA, List.of("en-GH", "ak", "ee", "tw")),
                new OpenCDXCountryModel(
                        "Gibraltar", "GI", "GIB", "gi", "GI", 292, "350", EUROPE, List.of("en-GI", "es", "it", "pt")),
                new OpenCDXCountryModel(
                        "Greece", "GR", "GRC", "gr", "GR", 300, "30", EUROPE, List.of("el-GR", "en", "fr")),
                new OpenCDXCountryModel(
                        "Greenland", "GL", "GRL", "gl", "GL", 304, "299", NORTH_AMERICA, List.of("kl", "da-GL", "en")),
                new OpenCDXCountryModel(
                        "Grenada", "GD", "GRD", "gd", "GJ", 308, "1-473", NORTH_AMERICA, List.of("en-GD")),
                new OpenCDXCountryModel(
                        "Guam", "GU", "GUM", "gu", "GQ", 316, "1-671", OCEANIA, List.of("en-GU", "ch-GU")),
                new OpenCDXCountryModel(
                        "Guatemala", "GT", "GTM", "gt", "GT", 320, "502", NORTH_AMERICA, List.of("es-GT")),
                new OpenCDXCountryModel(
                        "Guernsey", "GG", "GGY", "gg", "GK", 831, "44-1481", EUROPE, List.of("en", "fr")),
                new OpenCDXCountryModel("Guinea", "GN", "GIN", "gn", "GV", 324, "224", AFRICA, List.of("fr-GN")),
                new OpenCDXCountryModel(
                        "Guinea-Bissau", "GW", "GNB", "gw", "PU", 624, "245", AFRICA, List.of("pt-GW", "pov")),
                new OpenCDXCountryModel("Guyana", "GY", "GUY", "gy", "GY", 328, "592", SOUTH_AMERICA, List.of("en-GY")),
                new OpenCDXCountryModel(
                        "Haiti", "HT", "HTI", "ht", "HA", 332, "509", NORTH_AMERICA, List.of("ht", "fr-HT")),
                new OpenCDXCountryModel(
                        "Honduras", "HN", "HND", "hn", "HO", 340, "504", NORTH_AMERICA, List.of("es-HN")),
                new OpenCDXCountryModel(
                        "Hong Kong", "HK", "HKG", "hk", "HK", 344, "852", ASIA, List.of("zh-HK", "yue", "zh", "en")),
                new OpenCDXCountryModel("Hungary", "HU", "HUN", "hu", "HU", 348, "36", EUROPE, List.of("hu-HU")),
                new OpenCDXCountryModel(
                        "Iceland",
                        "IS",
                        "ISL",
                        "is",
                        "IC",
                        352,
                        "354",
                        EUROPE,
                        List.of("is", "en", "de", "da", "sv", "no")),
                new OpenCDXCountryModel(
                        "India",
                        "IN",
                        "IND",
                        "in",
                        "IN",
                        356,
                        "91",
                        ASIA,
                        List.of(
                                "en-IN", "hi", "bn", "te", "mr", "ta", "ur", "gu", "kn", "ml", "or", "pa", "as", "bh",
                                "sat", "ks", "ne", "sd", "kok", "doi", "mni", "sit", "sa", "fr", "lus", "inc")),
                new OpenCDXCountryModel(
                        "Indonesia", "ID", "IDN", "id", "ID", 360, "62", ASIA, List.of("id", "en", "nl", "jv")),
                new OpenCDXCountryModel("Iran", "IR", "IRN", "ir", "IR", 364, "98", ASIA, List.of("fa-IR", "ku")),
                new OpenCDXCountryModel(
                        "Iraq", "IQ", "IRQ", "iq", "IZ", 368, "964", ASIA, List.of("ar-IQ", "ku", "hy")),
                new OpenCDXCountryModel(
                        "Ireland", "IE", "IRL", "ie", "EI", 372, "353", EUROPE, List.of("en-IE", "ga-IE")),
                new OpenCDXCountryModel(
                        "Isle of Man", "IM", "IMN", "im", "IM", 833, "44-1624", EUROPE, List.of("en", "gv")),
                new OpenCDXCountryModel(
                        "Israel", "IL", "ISR", "il", "IS", 376, "972", ASIA, List.of("he", "ar-IL", "en-IL", null)),
                new OpenCDXCountryModel(
                        "Italy",
                        "IT",
                        "ITA",
                        "it",
                        "IT",
                        380,
                        "39",
                        EUROPE,
                        List.of("it-IT", "de-IT", "fr-IT", "sc", "ca", "co", "sl")),
                new OpenCDXCountryModel("Ivory Coast", "CI", "CIV", "ci", "IV", 384, "225", AFRICA, List.of("fr-CI")),
                new OpenCDXCountryModel(
                        "Jamaica", "JM", "JAM", "jm", "JM", 388, "1-876", NORTH_AMERICA, List.of("en-JM")),
                new OpenCDXCountryModel("Japan", "JP", "JPN", "jp", "JA", 392, "81", ASIA, List.of("ja")),
                new OpenCDXCountryModel("Jersey", "JE", "JEY", "je", "JE", 832, "44-1534", EUROPE, List.of("en", "pt")),
                new OpenCDXCountryModel("Jordan", "JO", "JOR", "jo", "JO", 400, "962", ASIA, List.of("ar-JO", "en")),
                new OpenCDXCountryModel("Kazakhstan", "KZ", "KAZ", "kz", "KZ", 398, "7", ASIA, List.of("kk", "ru")),
                new OpenCDXCountryModel(
                        "Kenya", "KE", "KEN", "ke", "KE", 404, "254", AFRICA, List.of("en-KE", "sw-KE")),
                new OpenCDXCountryModel(
                        "Kiribati", "KI", "KIR", "ki", "KR", 296, "686", OCEANIA, List.of("en-KI", "gil")),
                new OpenCDXCountryModel("Kosovo", "XK", "XKX", null, "KV", 0, "383", EUROPE, List.of("sq", "sr")),
                new OpenCDXCountryModel("Kuwait", "KW", "KWT", "kw", "KU", 414, "965", ASIA, List.of("ar-KW", "en")),
                new OpenCDXCountryModel(
                        "Kyrgyzstan", "KG", "KGZ", "kg", "KG", 417, "996", ASIA, List.of("ky", "uz", "ru")),
                new OpenCDXCountryModel("Laos", "LA", "LAO", "la", "LA", 418, "856", ASIA, List.of("lo", "fr", "en")),
                new OpenCDXCountryModel(
                        "Latvia", "LV", "LVA", "lv", "LG", 428, "371", EUROPE, List.of("lv", "ru", "lt")),
                new OpenCDXCountryModel(
                        "Lebanon", "LB", "LBN", "lb", "LE", 422, "961", ASIA, List.of("ar-LB", "fr-LB", "en", "hy")),
                new OpenCDXCountryModel(
                        "Lesotho", "LS", "LSO", "ls", "LT", 426, "266", AFRICA, List.of("en-LS", "st", "zu", "xh")),
                new OpenCDXCountryModel("Liberia", "LR", "LBR", "lr", "LI", 430, "231", AFRICA, List.of("en-LR")),
                new OpenCDXCountryModel(
                        "Libya", "LY", "LBY", "ly", "LY", 434, "218", AFRICA, List.of("ar-LY", "it", "en")),
                new OpenCDXCountryModel("Liechtenstein", "LI", "LIE", "li", "LS", 438, "423", EUROPE, List.of("de-LI")),
                new OpenCDXCountryModel(
                        "Lithuania", "LT", "LTU", "lt", "LH", 440, "370", EUROPE, List.of("lt", "ru", "pl")),
                new OpenCDXCountryModel(
                        "Luxembourg", "LU", "LUX", "lu", "LU", 442, "352", EUROPE, List.of("lb", "de-LU", "fr-LU")),
                new OpenCDXCountryModel(
                        "Macau", "MO", "MAC", "mo", "MC", 446, "853", ASIA, List.of("zh", "zh-MO", "pt")),
                new OpenCDXCountryModel(
                        "Macedonia",
                        "MK",
                        "MKD",
                        "mk",
                        "MK",
                        807,
                        "389",
                        EUROPE,
                        List.of("mk", "sq", "tr", "rmm", "sr")),
                new OpenCDXCountryModel(
                        "Madagascar", "MG", "MDG", "mg", "MA", 450, "261", AFRICA, List.of("fr-MG", "mg")),
                new OpenCDXCountryModel(
                        "Malawi", "MW", "MWI", "mw", "MI", 454, "265", AFRICA, List.of("ny", "yao", "tum", "swk")),
                new OpenCDXCountryModel(
                        "Malaysia",
                        "MY",
                        "MYS",
                        "my",
                        "MY",
                        458,
                        "60",
                        ASIA,
                        List.of("ms-MY", "en", "zh", "ta", "te", "ml", "pa", "th")),
                new OpenCDXCountryModel("Maldives", "MV", "MDV", "mv", "MV", 462, "960", ASIA, List.of("dv", "en")),
                new OpenCDXCountryModel("Mali", "ML", "MLI", "ml", "ML", 466, "223", AFRICA, List.of("fr-ML", "bm")),
                new OpenCDXCountryModel("Malta", "MT", "MLT", "mt", "MT", 470, "356", EUROPE, List.of("mt", "en-MT")),
                new OpenCDXCountryModel(
                        "Marshall Islands", "MH", "MHL", "mh", "RM", 584, "692", OCEANIA, List.of("mh", "en-MH")),
                new OpenCDXCountryModel(
                        "Mauritania",
                        "MR",
                        "MRT",
                        "mr",
                        "MR",
                        478,
                        "222",
                        AFRICA,
                        List.of("ar-MR", "fuc", "snk", "fr", "mey", "wo")),
                new OpenCDXCountryModel(
                        "Mauritius", "MU", "MUS", "mu", "MP", 480, "230", AFRICA, List.of("en-MU", "bho", "fr")),
                new OpenCDXCountryModel("Mayotte", "YT", "MYT", "yt", "MF", 175, "262", AFRICA, List.of("fr-YT")),
                new OpenCDXCountryModel("Mexico", "MX", "MEX", "mx", "MX", 484, "52", NORTH_AMERICA, List.of("es-MX")),
                new OpenCDXCountryModel(
                        "Micronesia",
                        "FM",
                        "FSM",
                        "fm",
                        "FM",
                        583,
                        "691",
                        OCEANIA,
                        List.of("en-FM", "chk", "pon", "yap", "kos", "uli", "woe", "nkr", "kpg")),
                new OpenCDXCountryModel(
                        "Moldova", "MD", "MDA", "md", "MD", 498, "373", EUROPE, List.of("ro", "ru", "gag", "tr")),
                new OpenCDXCountryModel(
                        "Monaco", "MC", "MCO", "mc", "MN", 492, "377", EUROPE, List.of("fr-MC", "en", "it")),
                new OpenCDXCountryModel("Mongolia", "MN", "MNG", "mn", "MG", 496, "976", ASIA, List.of("mn", "ru")),
                new OpenCDXCountryModel(
                        "Montenegro",
                        "ME",
                        "MNE",
                        "me",
                        "MJ",
                        499,
                        "382",
                        EUROPE,
                        List.of("sr", "hu", "bs", "sq", "hr", "rom")),
                new OpenCDXCountryModel(
                        "Montserrat", "MS", "MSR", "ms", "MH", 500, "1-664", NORTH_AMERICA, List.of("en-MS")),
                new OpenCDXCountryModel("Morocco", "MA", "MAR", "ma", "MO", 504, "212", AFRICA, List.of("ar-MA", "fr")),
                new OpenCDXCountryModel(
                        "Mozambique", "MZ", "MOZ", "mz", "MZ", 508, "258", AFRICA, List.of("pt-MZ", "vmw")),
                new OpenCDXCountryModel("Myanmar", "MM", "MMR", "mm", "BM", 104, "95", ASIA, List.of("my")),
                new OpenCDXCountryModel(
                        "Namibia",
                        "NA",
                        "NAM",
                        "na",
                        "WA",
                        516,
                        "264",
                        AFRICA,
                        List.of("en-NA", "af", "de", "hz", "naq")),
                new OpenCDXCountryModel("Nauru", "NR", "NRU", "nr", "NR", 520, "674", OCEANIA, List.of("na", "en-NR")),
                new OpenCDXCountryModel("Nepal", "NP", "NPL", "np", "NP", 524, "977", ASIA, List.of("ne", "en")),
                new OpenCDXCountryModel(
                        "Netherlands", "NL", "NLD", "nl", "NL", 528, "31", EUROPE, List.of("nl-NL", "fy-NL")),
                new OpenCDXCountryModel(
                        "Netherlands Antilles",
                        "AN",
                        "ANT",
                        "an",
                        "NT",
                        530,
                        "599",
                        NORTH_AMERICA,
                        List.of("nl-AN", "en", "es")),
                new OpenCDXCountryModel(
                        "New Caledonia", "NC", "NCL", "nc", "NC", 540, "687", OCEANIA, List.of("fr-NC")),
                new OpenCDXCountryModel(
                        "New Zealand", "NZ", "NZL", "nz", "NZ", 554, "64", OCEANIA, List.of("en-NZ", "mi")),
                new OpenCDXCountryModel(
                        "Nicaragua", "NI", "NIC", "ni", "NU", 558, "505", NORTH_AMERICA, List.of("es-NI", "en")),
                new OpenCDXCountryModel(
                        "Niger", "NE", "NER", "ne", "NG", 562, "227", AFRICA, List.of("fr-NE", "ha", "kr", "dje")),
                new OpenCDXCountryModel(
                        "Nigeria",
                        "NG",
                        "NGA",
                        "ng",
                        "NI",
                        566,
                        "234",
                        AFRICA,
                        List.of("en-NG", "ha", "yo", "ig", "ff")),
                new OpenCDXCountryModel("Niue", "NU", "NIU", "nu", "NE", 570, "683", OCEANIA, List.of("niu", "en-NU")),
                new OpenCDXCountryModel("North Korea", "KP", "PRK", "kp", "KN", 408, "850", ASIA, List.of("ko-KP")),
                new OpenCDXCountryModel(
                        "Northern Mariana Islands",
                        "MP",
                        "MNP",
                        "mp",
                        "CQ",
                        580,
                        "1-670",
                        OCEANIA,
                        List.of("fil", "tl", "zh", "ch-MP", "en-MP")),
                new OpenCDXCountryModel(
                        "Norway", "NO", "NOR", "no", "NO", 578, "47", EUROPE, List.of("no", "nb", "nn", "se", "fi")),
                new OpenCDXCountryModel(
                        "Oman", "OM", "OMN", "om", "MU", 512, "968", ASIA, List.of("ar-OM", "en", "bal", "ur")),
                new OpenCDXCountryModel(
                        "Pakistan",
                        "PK",
                        "PAK",
                        "pk",
                        "PK",
                        586,
                        "92",
                        ASIA,
                        List.of("ur-PK", "en-PK", "pa", "sd", "ps", "brh")),
                new OpenCDXCountryModel(
                        "Palau",
                        "PW",
                        "PLW",
                        "pw",
                        "PS",
                        585,
                        "680",
                        OCEANIA,
                        List.of("pau", "sov", "en-PW", "tox", "ja", "fil", "zh")),
                new OpenCDXCountryModel("Palestine", "PS", "PSE", "ps", "WE", 275, "970", ASIA, List.of("ar-PS")),
                new OpenCDXCountryModel(
                        "Panama", "PA", "PAN", "pa", "PM", 591, "507", NORTH_AMERICA, List.of("es-PA", "en")),
                new OpenCDXCountryModel(
                        "Papua New Guinea",
                        "PG",
                        "PNG",
                        "pg",
                        "PP",
                        598,
                        "675",
                        OCEANIA,
                        List.of("en-PG", "ho", "meu", "tpi")),
                new OpenCDXCountryModel(
                        "Paraguay", "PY", "PRY", "py", "PA", 600, "595", SOUTH_AMERICA, List.of("es-PY", "gn")),
                new OpenCDXCountryModel(
                        "Peru", "PE", "PER", "pe", "PE", 604, "51", SOUTH_AMERICA, List.of("es-PE", "qu", "ay")),
                new OpenCDXCountryModel(
                        "Philippines", "PH", "PHL", "ph", "RP", 608, "63", ASIA, List.of("tl", "en-PH", "fil")),
                new OpenCDXCountryModel("Pitcairn", "PN", "PCN", "pn", "PC", 612, "64", OCEANIA, List.of("en-PN")),
                new OpenCDXCountryModel("Poland", "PL", "POL", "pl", "PL", 616, "48", EUROPE, List.of("pl")),
                new OpenCDXCountryModel(
                        "Portugal", "PT", "PRT", "pt", "PO", 620, "351", EUROPE, List.of("pt-PT", "mwl")),
                new OpenCDXCountryModel(
                        "Puerto Rico",
                        "PR",
                        "PRI",
                        "pr",
                        "RQ",
                        630,
                        "1-787, 1-939",
                        NORTH_AMERICA,
                        List.of("en-PR", "es-PR")),
                new OpenCDXCountryModel("Qatar", "QA", "QAT", "qa", "QA", 634, "974", ASIA, List.of("ar-QA", "es")),
                new OpenCDXCountryModel(
                        "Republic of the Congo",
                        "CG",
                        "COG",
                        "cg",
                        "CF",
                        178,
                        "242",
                        AFRICA,
                        List.of("fr-CG", "kg", "ln-CG")),
                new OpenCDXCountryModel("Reunion", "RE", "REU", "re", "RE", 638, "262", AFRICA, List.of("fr-RE")),
                new OpenCDXCountryModel(
                        "Romania", "RO", "ROU", "ro", "RO", 642, "40", EUROPE, List.of("ro", "hu", "rom")),
                new OpenCDXCountryModel(
                        "Russia",
                        "RU",
                        "RUS",
                        "ru",
                        "RS",
                        643,
                        "7",
                        EUROPE,
                        List.of(
                                "ru", "tt", "xal", "cau", "ady", "kv", "ce", "tyv", "cv", "udm", "tut", "mns", "bua",
                                "myv", "mdf", "chm", "ba", "inh", "tut", "kbd", "krc", "ava", "sah", "nog")),
                new OpenCDXCountryModel(
                        "Rwanda", "RW", "RWA", "rw", "RW", 646, "250", AFRICA, List.of("rw", "en-RW", "fr-RW", "sw")),
                new OpenCDXCountryModel(
                        "Saint Barthelemy", "BL", "BLM", "gp", "TB", 652, "590", NORTH_AMERICA, List.of("fr")),
                new OpenCDXCountryModel("Saint Helena", "SH", "SHN", "sh", "SH", 654, "290", AFRICA, List.of("en-SH")),
                new OpenCDXCountryModel(
                        "Saint Kitts and Nevis",
                        "KN",
                        "KNA",
                        "kn",
                        "SC",
                        659,
                        "1-869",
                        NORTH_AMERICA,
                        List.of("en-KN")),
                new OpenCDXCountryModel(
                        "Saint Lucia", "LC", "LCA", "lc", "ST", 662, "1-758", NORTH_AMERICA, List.of("en-LC")),
                new OpenCDXCountryModel(
                        "Saint Martin", "MF", "MAF", "gp", "RN", 663, "590", NORTH_AMERICA, List.of("fr")),
                new OpenCDXCountryModel(
                        "Saint Pierre and Miquelon",
                        "PM",
                        "SPM",
                        "pm",
                        "SB",
                        666,
                        "508",
                        NORTH_AMERICA,
                        List.of("fr-PM")),
                new OpenCDXCountryModel(
                        "Saint Vincent and the Grenadines",
                        "VC",
                        "VCT",
                        "vc",
                        "VC",
                        670,
                        "1-784",
                        NORTH_AMERICA,
                        List.of("en-VC", "fr")),
                new OpenCDXCountryModel("Samoa", "WS", "WSM", "ws", "WS", 882, "685", OCEANIA, List.of("sm", "en-WS")),
                new OpenCDXCountryModel("San Marino", "SM", "SMR", "sm", "SM", 674, "378", EUROPE, List.of("it-SM")),
                new OpenCDXCountryModel(
                        "Sao Tome and Principe", "ST", "STP", "st", "TP", 678, "239", AFRICA, List.of("pt-ST")),
                new OpenCDXCountryModel("Saudi Arabia", "SA", "SAU", "sa", "SA", 682, "966", ASIA, List.of("ar-SA")),
                new OpenCDXCountryModel(
                        "Senegal", "SN", "SEN", "sn", "SG", 686, "221", AFRICA, List.of("fr-SN", "wo", "fuc", "mnk")),
                new OpenCDXCountryModel(
                        "Serbia", "RS", "SRB", "rs", "RI", 688, "381", EUROPE, List.of("sr", "hu", "bs", "rom")),
                new OpenCDXCountryModel(
                        "Seychelles", "SC", "SYC", "sc", "SE", 690, "248", AFRICA, List.of("en-SC", "fr-SC")),
                new OpenCDXCountryModel(
                        "Sierra Leone", "SL", "SLE", "sl", "SL", 694, "232", AFRICA, List.of("en-SL", "men", "tem")),
                new OpenCDXCountryModel(
                        "Singapore",
                        "SG",
                        "SGP",
                        "sg",
                        "SN",
                        702,
                        "65",
                        ASIA,
                        List.of("cmn", "en-SG", "ms-SG", "ta-SG", "zh-SG")),
                new OpenCDXCountryModel(
                        "Sint Maarten", "SX", "SXM", "sx", "NN", 534, "1-721", NORTH_AMERICA, List.of("nl", "en")),
                new OpenCDXCountryModel("Slovakia", "SK", "SVK", "sk", "LO", 703, "421", EUROPE, List.of("sk", "hu")),
                new OpenCDXCountryModel("Slovenia", "SI", "SVN", "si", "SI", 705, "386", EUROPE, List.of("sl", "sh")),
                new OpenCDXCountryModel(
                        "Solomon Islands", "SB", "SLB", "sb", "BP", 90, "677", OCEANIA, List.of("en-SB", "tpi")),
                new OpenCDXCountryModel(
                        "Somalia",
                        "SO",
                        "SOM",
                        "so",
                        "SO",
                        706,
                        "252",
                        AFRICA,
                        List.of("so-SO", "ar-SO", "it", "en-SO")),
                new OpenCDXCountryModel(
                        "South Africa",
                        "ZA",
                        "ZAF",
                        "za",
                        "SF",
                        710,
                        "27",
                        AFRICA,
                        List.of("zu", "xh", "af", "nso", "en-ZA", "tn", "st", "ts", "ss", "ve", "nr")),
                new OpenCDXCountryModel(
                        "South Korea", "KR", "KOR", "kr", "KS", 410, "82", ASIA, List.of("ko-KR", "en")),
                new OpenCDXCountryModel("South Sudan", "SS", "SSD", "ss", "OD", 728, "211", AFRICA, List.of("en")),
                new OpenCDXCountryModel(
                        "Spain", "ES", "ESP", "es", "SP", 724, "34", EUROPE, List.of("es-ES", "ca", "gl", "eu", "oc")),
                new OpenCDXCountryModel(
                        "Sri Lanka", "LK", "LKA", "lk", "CE", 144, "94", ASIA, List.of("si", "ta", "en")),
                new OpenCDXCountryModel(
                        "Sudan", "SD", "SDN", "sd", "SU", 729, "249", AFRICA, List.of("ar-SD", "en", "fia")),
                new OpenCDXCountryModel(
                        "Suriname",
                        "SR",
                        "SUR",
                        "sr",
                        "NS",
                        740,
                        "597",
                        SOUTH_AMERICA,
                        List.of("nl-SR", "en", "srn", "hns", "jv")),
                new OpenCDXCountryModel(
                        "Svalbard and Jan Mayen", "SJ", "SJM", "sj", "SV", 744, "47", EUROPE, List.of("no", "ru")),
                new OpenCDXCountryModel(
                        "Swaziland", "SZ", "SWZ", "sz", "WZ", 748, "268", AFRICA, List.of("en-SZ", "ss-SZ")),
                new OpenCDXCountryModel(
                        "Sweden", "SE", "SWE", "se", "SW", 752, "46", EUROPE, List.of("sv-SE", "se", "sma", "fi-SE")),
                new OpenCDXCountryModel(
                        "Switzerland",
                        "CH",
                        "CHE",
                        "ch",
                        "SZ",
                        756,
                        "41",
                        EUROPE,
                        List.of("de-CH", "fr-CH", "it-CH", "rm")),
                new OpenCDXCountryModel(
                        "Syria",
                        "SY",
                        "SYR",
                        "sy",
                        "SY",
                        760,
                        "963",
                        ASIA,
                        List.of("ar-SY", "ku", "hy", "arc", "fr", "en")),
                new OpenCDXCountryModel(
                        "Taiwan", "TW", "TWN", "tw", "TW", 158, "886", ASIA, List.of("zh-TW", "zh", "nan", "hak")),
                new OpenCDXCountryModel("Tajikistan", "TJ", "TJK", "tj", "TI", 762, "992", ASIA, List.of("tg", "ru")),
                new OpenCDXCountryModel(
                        "Tanzania", "TZ", "TZA", "tz", "TZ", 834, "255", AFRICA, List.of("sw-TZ", "en", "ar")),
                new OpenCDXCountryModel("Thailand", "TH", "THA", "th", "TH", 764, "66", ASIA, List.of("th", "en")),
                new OpenCDXCountryModel(
                        "Togo",
                        "TG",
                        "TGO",
                        "tg",
                        "TO",
                        768,
                        "228",
                        AFRICA,
                        List.of("fr-TG", "ee", "hna", "kbp", "dag", "ha")),
                new OpenCDXCountryModel(
                        "Tokelau", "TK", "TKL", "tk", "TL", 772, "690", OCEANIA, List.of("tkl", "en-TK")),
                new OpenCDXCountryModel("Tonga", "TO", "TON", "to", "TN", 776, "676", OCEANIA, List.of("to", "en-TO")),
                new OpenCDXCountryModel(
                        "Trinidad and Tobago",
                        "TT",
                        "TTO",
                        "tt",
                        "TD",
                        780,
                        "1-868",
                        NORTH_AMERICA,
                        List.of("en-TT", "hns", "fr", "es", "zh")),
                new OpenCDXCountryModel("Tunisia", "TN", "TUN", "tn", "TS", 788, "216", AFRICA, List.of("ar-TN", "fr")),
                new OpenCDXCountryModel(
                        "Turkey",
                        "TR",
                        "TUR",
                        "tr",
                        "TU",
                        792,
                        "90",
                        ASIA,
                        List.of("tr-TR", "ku", "diq", "az", "av")),
                new OpenCDXCountryModel(
                        "Turkmenistan", "TM", "TKM", "tm", "TX", 795, "993", ASIA, List.of("tk", "ru", "uz")),
                new OpenCDXCountryModel(
                        "Turks and Caicos Islands",
                        "TC",
                        "TCA",
                        "tc",
                        "TK",
                        796,
                        "1-649",
                        NORTH_AMERICA,
                        List.of("en-TC")),
                new OpenCDXCountryModel(
                        "Tuvalu", "TV", "TUV", "tv", "TV", 798, "688", OCEANIA, List.of("tvl", "en", "sm", "gil")),
                new OpenCDXCountryModel(
                        "U.S. Virgin Islands", "VI", "VIR", "vi", "VQ", 850, "1-340", NORTH_AMERICA, List.of("en-VI")),
                new OpenCDXCountryModel(
                        "Uganda", "UG", "UGA", "ug", "UG", 800, "256", AFRICA, List.of("en-UG", "lg", "sw", "ar")),
                new OpenCDXCountryModel(
                        "Ukraine",
                        "UA",
                        "UKR",
                        "ua",
                        "UP",
                        804,
                        "380",
                        EUROPE,
                        List.of("uk", "ru-UA", "rom", "pl", "hu")),
                new OpenCDXCountryModel(
                        "United Arab Emirates",
                        "AE",
                        "ARE",
                        "ae",
                        "AE",
                        784,
                        "971",
                        ASIA,
                        List.of("ar-AE", "fa", "en", "hi", "ur")),
                new OpenCDXCountryModel(
                        "United Kingdom", "GB", "GBR", "uk", "UK", 826, "44", EUROPE, List.of("en-GB", "cy-GB", "gd")),
                new OpenCDXCountryModel(
                        "United States",
                        "US",
                        "USA",
                        "us",
                        "US",
                        840,
                        "1",
                        NORTH_AMERICA,
                        List.of("en-US", "es-US", "haw", "fr")),
                new OpenCDXCountryModel(
                        "Uruguay", "UY", "URY", "uy", "UY", 858, "598", SOUTH_AMERICA, List.of("es-UY")),
                new OpenCDXCountryModel(
                        "Uzbekistan", "UZ", "UZB", "uz", "UZ", 860, "998", ASIA, List.of("uz", "ru", "tg")),
                new OpenCDXCountryModel(
                        "Vanuatu", "VU", "VUT", "vu", "NH", 548, "678", OCEANIA, List.of("bi", "en-VU", "fr-VU")),
                new OpenCDXCountryModel(
                        "Vatican", "VA", "VAT", "va", "VT", 336, "379", EUROPE, List.of("la", "it", "fr")),
                new OpenCDXCountryModel(
                        "Venezuela", "VE", "VEN", "ve", "VE", 862, "58", SOUTH_AMERICA, List.of("es-VE")),
                new OpenCDXCountryModel(
                        "Vietnam", "VN", "VNM", "vn", "VM", 704, "84", ASIA, List.of("vi", "en", "fr", "zh", "km")),
                new OpenCDXCountryModel(
                        "Wallis and Futuna",
                        "WF",
                        "WLF",
                        "wf",
                        "WF",
                        876,
                        "681",
                        OCEANIA,
                        List.of("wls", "fud", "fr-WF")),
                new OpenCDXCountryModel(
                        "Western Sahara", "EH", "ESH", "eh", "WI", 732, "212", AFRICA, List.of("ar", "mey")),
                new OpenCDXCountryModel("Yemen", "YE", "YEM", "ye", "YM", 887, "967", ASIA, List.of("ar-YE")),
                new OpenCDXCountryModel(
                        "Zambia",
                        "ZM",
                        "ZMB",
                        "zm",
                        "ZA",
                        894,
                        "260",
                        AFRICA,
                        List.of("en-ZM", "bem", "loz", "lun", "lue", "ny", "toi")),
                new OpenCDXCountryModel(
                        "Zimbabwe", "ZW", "ZWE", "zw", "ZI", 716, "263", AFRICA, List.of("en-ZW", "sn", "nr", "nd"))));
    }
}
