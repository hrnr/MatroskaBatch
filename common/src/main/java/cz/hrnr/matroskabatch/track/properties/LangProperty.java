/* 
 * The MIT License
 *
 * Copyright 2015 Jiri Horner.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package cz.hrnr.matroskabatch.track.properties;

/**
 * Represents language Matroska property
 * 
 * Language: Specifies the language of the track in the
 * Matroska languages form.
 * 
 * @see mkvpropedit -l
 */
public enum LangProperty {

	ABK("Abkhazian"), ACE("Achinese"), ACH("Acoli"), ADA("Adangme"), ADY("Adyghe; Adygei"), AAR("Afar"), AFH("Afrihili"), AFR("Afrikaans"), AFA("Afro-Asiatic languages"), AIN("Ainu"), AKA("Akan"), AKK("Akkadian"), ALB("Albanian"), ALE("Aleut"), ALG("Algonquian languages"), TUT("Altaic languages"), AMH("Amharic"), ANP("Angika"), APA("Apache languages"), ARA("Arabic"), ARG("Aragonese"), ARP("Arapaho"), ARW("Arawak"), ARM("Armenian"), RUP("Aromanian; Arumanian; Macedo-Romanian"), ART("Artificial languages"), ASM("Assamese"), AST("Asturian; Bable; Leonese; Asturleonese"), ATH("Athapascan languages"), AUS("Australian languages"), MAP("Austronesian languages"), AVA("Avaric"), AVE("Avestan"), AWA("Awadhi"), AYM("Aymara"), AZE("Azerbaijani"), BAN("Balinese"), BAT("Baltic languages"), BAL("Baluchi"), BAM("Bambara"), BAI("Bamileke languages"), BAD("Banda languages"), BNT("Bantu (Other)"), BAS("Basa"), BAK("Bashkir"), BAQ("Basque"), BTK("Batak languages"), BEJ("Beja; Bedawiyet"), BEL("Belarusian"), BEM("Bemba"), BEN("Bengali"), BER("Berber languages"), BHO("Bhojpuri"), BIH("Bihari languages"), BIK("Bikol"), BIN("Bini; Edo"), BIS("Bislama"), BYN("Blin; Bilin"), ZBL("Blissymbols; Blissymbolics; Bliss"), NOB("Bokmål, Norwegian; Norwegian Bokmål"), BOS("Bosnian"), BRA("Braj"), BRE("Breton"), BUG("Buginese"), BUL("Bulgarian"), BUA("Buriat"), BUR("Burmese"), CAD("Caddo"), CAT("Catalan; Valencian"), CAU("Caucasian languages"), CEB("Cebuano"), CEL("Celtic languages"), CAI("Central American Indian languages"), KHM("Central Khmer"), CHG("Chagatai"), CMC("Chamic languages"), CHA("Chamorro"), CHE("Chechen"), CHR("Cherokee"), CHY("Cheyenne"), CHB("Chibcha"), NYA("Chichewa; Chewa; Nyanja"), CHI("Chinese"), CHN("Chinook jargon"), CHP("Chipewyan; Dene Suline"), CHO("Choctaw"), CHU("Church Slavic; Old Slavonic; Church Slavonic; Old Bulgarian; Old Church Slavonic"), CHK("Chuukese"), CHV("Chuvash"), NWC("Classical Newari; Old Newari; Classical Nepal Bhasa"), SYC("Classical Syriac"), COP("Coptic"), COR("Cornish"), COS("Corsican"), CRE("Cree"), MUS("Creek"), CRP("Creoles and pidgins"), CPE("Creoles and pidgins, English based"), CPF("Creoles and pidgins, French-based"), CPP("Creoles and pidgins, Portuguese-based"), CRH("Crimean Tatar; Crimean Turkish"), HRV("Croatian"), CUS("Cushitic languages"), CZE("Czech"), DAK("Dakota"), DAN("Danish"), DAR("Dargwa"), DEL("Delaware"), DIN("Dinka"), DIV("Divehi; Dhivehi; Maldivian"), DOI("Dogri"), DGR("Dogrib"), DRA("Dravidian languages"), DUA("Duala"), DUM("Dutch, Middle (ca.1050-1350)"), DUT("Dutch; Flemish"), DYU("Dyula"), DZO("Dzongkha"), FRS("Eastern Frisian"), EFI("Efik"), EGY("Egyptian (Ancient)"), EKA("Ekajuk"), ELX("Elamite"), ENG("English"), ENM("English, Middle (1100-1500)"), ANG("English, Old (ca.450-1100)"), MYV("Erzya"), EPO("Esperanto"), EST("Estonian"), EWE("Ewe"), EWO("Ewondo"), FAN("Fang"), FAT("Fanti"), FAO("Faroese"), FIJ("Fijian"), FIL("Filipino; Pilipino"), FIN("Finnish"), FIU("Finno-Ugrian languages"), FON("Fon"), FRE("French"), FRM("French, Middle (ca.1400-1600)"), FRO("French, Old (842-ca.1400)"), FUR("Friulian"), FUL("Fulah"), GAA("Ga"), GLA("Gaelic; Scottish Gaelic"), CAR("Galibi Carib"), GLG("Galician"), LUG("Ganda"), GAY("Gayo"), GBA("Gbaya"), GEZ("Geez"), GEO("Georgian"), GER("German"), GMH("German, Middle High (ca.1050-1500)"), GOH("German, Old High (ca.750-1050)"), GEM("Germanic languages"), GIL("Gilbertese"), GON("Gondi"), GOR("Gorontalo"), GOT("Gothic"), GRB("Grebo"), GRC("Greek, Ancient (to 1453)"), GRE("Greek, Modern (1453-)"), GRN("Guarani"), GUJ("Gujarati"), GWI("Gwich'in"), HAI("Haida"), HAT("Haitian; Haitian Creole"), HAU("Hausa"), HAW("Hawaiian"), HEB("Hebrew"), HER("Herero"), HIL("Hiligaynon"), HIM("Himachali languages; Western Pahari languages"), HIN("Hindi"), HMO("Hiri Motu"), HIT("Hittite"), HMN("Hmong; Mong"), HUN("Hungarian"), HUP("Hupa"), IBA("Iban"), ICE("Icelandic"), IDO("Ido"), IBO("Igbo"), IJO("Ijo languages"), ILO("Iloko"), SMN("Inari Sami"), INC("Indic languages"), INE("Indo-European languages"), IND("Indonesian"), INH("Ingush"), INA("Interlingua (International Auxiliary Language Association)"), ILE("Interlingue; Occidental"), IKU("Inuktitut"), IPK("Inupiaq"), IRA("Iranian languages"), GLE("Irish"), MGA("Irish, Middle (900-1200)"), SGA("Irish, Old (to 900)"), IRO("Iroquoian languages"), ITA("Italian"), JPN("Japanese"), JAV("Javanese"), JRB("Judeo-Arabic"), JPR("Judeo-Persian"), KBD("Kabardian"), KAB("Kabyle"), KAC("Kachin; Jingpho"), KAL("Kalaallisut; Greenlandic"), XAL("Kalmyk; Oirat"), KAM("Kamba"), KAN("Kannada"), KAU("Kanuri"), KAA("Kara-Kalpak"), KRC("Karachay-Balkar"), KRL("Karelian"), KAR("Karen languages"), KAS("Kashmiri"), CSB("Kashubian"), KAW("Kawi"), KAZ("Kazakh"), KHA("Khasi"), KHI("Khoisan languages"), KHO("Khotanese; Sakan"), KIK("Kikuyu; Gikuyu"), KMB("Kimbundu"), KIN("Kinyarwanda"), KIR("Kirghiz; Kyrgyz"), TLH("Klingon; tlhIngan-Hol"), KOM("Komi"), KON("Kongo"), KOK("Konkani"), KOR("Korean"), KOS("Kosraean"), KPE("Kpelle"), KRO("Kru languages"), KUA("Kuanyama; Kwanyama"), KUM("Kumyk"), KUR("Kurdish"), KRU("Kurukh"), KUT("Kutenai"), LAD("Ladino"), LAH("Lahnda"), LAM("Lamba"), DAY("Land Dayak languages"), LAO("Lao"), LAT("Latin"), LAV("Latvian"), LEZ("Lezghian"), LIM("Limburgan; Limburger; Limburgish"), LIN("Lingala"), LIT("Lithuanian"), JBO("Lojban"), NDS("Low German; Low Saxon; German, Low; Saxon, Low"), DSB("Lower Sorbian"), LOZ("Lozi"), LUB("Luba-Katanga"), LUA("Luba-Lulua"), LUI("Luiseno"), SMJ("Lule Sami"), LUN("Lunda"), LUO("Luo (Kenya and Tanzania)"), LUS("Lushai"), LTZ("Luxembourgish; Letzeburgesch"), MAC("Macedonian"), MAD("Madurese"), MAG("Magahi"), MAI("Maithili"), MAK("Makasar"), MLG("Malagasy"), MAY("Malay"), MAL("Malayalam"), MLT("Maltese"), MNC("Manchu"), MDR("Mandar"), MAN("Mandingo"), MNI("Manipuri"), MNO("Manobo languages"), GLV("Manx"), MAO("Maori"), ARN("Mapudungun; Mapuche"), MAR("Marathi"), CHM("Mari"), MAH("Marshallese"), MWR("Marwari"), MAS("Masai"), MYN("Mayan languages"), MEN("Mende"), MIC("Mi'kmaq; Micmac"), MIN("Minangkabau"), MWL("Mirandese"), MOH("Mohawk"), MDF("Moksha"), MKH("Mon-Khmer languages"), LOL("Mongo"), MON("Mongolian"), MOS("Mossi"), MUL("Multiple languages"), MUN("Munda languages"), NQO("N'Ko"), NAH("Nahuatl languages"), NAU("Nauru"), NAV("Navajo; Navaho"), NDE("Ndebele, North; North Ndebele"), NBL("Ndebele, South; South Ndebele"), NDO("Ndonga"), NAP("Neapolitan"), NEW("Nepal Bhasa; Newari"), NEP("Nepali"), NIA("Nias"), NIC("Niger-Kordofanian languages"), SSA("Nilo-Saharan languages"), NIU("Niuean"), ZXX("No linguistic content; Not applicable"), NOG("Nogai"), NON("Norse, Old"), NAI("North American Indian languages"), FRR("Northern Frisian"), SME("Northern Sami"), NNO("Norwegian Nynorsk; Nynorsk, Norwegian"), NOR("Norwegian"), NUB("Nubian languages"), NYM("Nyamwezi"), NYN("Nyankole"), NYO("Nyoro"), NZI("Nzima"), OCI("Occitan (post 1500)"), ARC("Official Aramaic (700-300 BCE); Imperial Aramaic (700-300 BCE)"), OJI("Ojibwa"), ORI("Oriya"), ORM("Oromo"), OSA("Osage"), OSS("Ossetian; Ossetic"), OTO("Otomian languages"), PAL("Pahlavi"), PAU("Palauan"), PLI("Pali"), PAM("Pampanga; Kapampangan"), PAG("Pangasinan"), PAN("Panjabi; Punjabi"), PAP("Papiamento"), PAA("Papuan languages"), NSO("Pedi; Sepedi; Northern Sotho"), PER("Persian"), PEO("Persian, Old (ca.600-400 B.C.)"), PHI("Philippine languages"), PHN("Phoenician"), PON("Pohnpeian"), POL("Polish"), POR("Portuguese"), PRA("Prakrit languages"), PRO("Provençal, Old (to 1500);Occitan, Old (to 1500)"), PUS("Pushto; Pashto"), QUE("Quechua"), RAJ("Rajasthani"), RAP("Rapanui"), RAR("Rarotongan; Cook Islands Maori"), ROA("Romance languages"), RUM("Romanian; Moldavian; Moldovan"), ROH("Romansh"), ROM("Romany"), RUN("Rundi"), RUS("Russian"), SAL("Salishan languages"), SAM("Samaritan Aramaic"), SMI("Sami languages"), SMO("Samoan"), SAD("Sandawe"), SAG("Sango"), SAN("Sanskrit"), SAT("Santali"), SRD("Sardinian"), SAS("Sasak"), SCO("Scots"), SEL("Selkup"), SEM("Semitic languages"), SRP("Serbian"), SRR("Serer"), SHN("Shan"), SNA("Shona"), III("Sichuan Yi; Nuosu"), SCN("Sicilian"), SID("Sidamo"), SGN("Sign Languages"), BLA("Siksika"), SND("Sindhi"), SIN("Sinhala; Sinhalese"), SIT("Sino-Tibetan languages"), SIO("Siouan languages"), SMS("Skolt Sami"), DEN("Slave (Athapascan)"), SLA("Slavic languages"), SLO("Slovak"), SLV("Slovenian"), SOG("Sogdian"), SOM("Somali"), SON("Songhai languages"), SNK("Soninke"), WEN("Sorbian languages"), SOT("Sotho, Southern"), SAI("South American Indian (Other)"), ALT("Southern Altai"), SMA("Southern Sami"), SPA("Spanish; Castillan"), SRN("Sranan Tongo"), SUK("Sukuma"), SUX("Sumerian"), SUN("Sundanese"), SUS("Susu"), SWA("Swahili"), SSW("Swati"), SWE("Swedish"), GSW("Swiss German; Alemannic; Alsatian"), SYR("Syriac"), TGL("Tagalog"), TAH("Tahitian"), TAI("Tai languages"), TGK("Tajik"), TMH("Tamashek"), TAM("Tamil"), TAT("Tatar"), TEL("Telugu"), TER("Tereno"), TET("Tetum"), THA("Thai"), TIB("Tibetan"), TIG("Tigre"), TIR("Tigrinya"), TEM("Timne"), TIV("Tiv"), TLI("Tlingit"), TPI("Tok Pisin"), TKL("Tokelau"), TOG("Tonga (Nyasa)"), TON("Tonga (Tonga Islands)"), TSI("Tsimshian"), TSO("Tsonga"), TSN("Tswana"), TUM("Tumbuka"), TUP("Tupi languages"), TUR("Turkish"), OTA("Turkish, Ottoman (1500-1928)"), TUK("Turkmen"), TVL("Tuvalu"), TYV("Tuvinian"), TWI("Twi"), UDM("Udmurt"), UGA("Ugaritic"), UIG("Uighur; Uyghur"), UKR("Ukrainian"), UMB("Umbundu"), MIS("Uncoded languages"), UND("Undetermined"), HSB("Upper Sorbian"), URD("Urdu"), UZB("Uzbek"), VAI("Vai"), VEN("Venda"), VIE("Vietnamese"), VOL("Volapük"), VOT("Votic"), WAK("Wakashan languages"), WAL("Walamo"), WLN("Walloon"), WAR("Waray"), WAS("Washo"), WEL("Welsh"), FRY("Western Frisian"), WOL("Wolof"), XHO("Xhosa"), SAH("Yakut"), YAO("Yao"), YAP("Yapese"), YID("Yiddish"), YOR("Yoruba"), YPK("Yupik languages"), ZND("Zande languages"), ZAP("Zapotec"), ZZA("Zaza; Dimili; Dimli; Kirdki; Kirmanjki; Zazaki"), ZEN("Zenaga"), ZHA("Zhuang; Chuang"), ZUL("Zulu"), ZUN("Zuni");

	private final String fullName;
	
	/**
	 * 
	 * @param fullName Name of language as specified by ISO
	 */
	private LangProperty(String fullName) {
		this.fullName = fullName;
	}
	
	/**
	 * @return ISO code (short form - 3 letters) of language
	 */
	public String toISOId() {
		return super.toString().toLowerCase();
	}

	@Override
	public String toString() {
		return super.toString().toLowerCase() + " (" + fullName + ")";
	}
}
