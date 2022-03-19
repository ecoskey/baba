package baba.util

case class Lang private(str: String) extends AnyVal

object Lang {
	/** Afrikaans */
	val af_za : Lang = Lang("af_za")

	/** Arabic */
	val ar_sa : Lang = Lang("ar_sa")

	/** Asturian */
	val ast_es: Lang = Lang("ast_es")

	/** Azerbaijani */
	val az_az : Lang = Lang("az_az")

	/** Belarusian */
	val be_by : Lang = Lang("be_by")

	/** Bulgarian*/
	val bg_bg : Lang = Lang("bg_bg")

	/** Breton */
	val br_fr : Lang = Lang("br_fr")

	/** Brabantian */
	val brb   : Lang = Lang("brb")

	/** Bosnian */
	val bs_BA : Lang = Lang("bs_BA")

	/** Catalan */
	val ca_es : Lang = Lang("ca_es")

	/** Czech */
	val cs_cz : Lang = Lang("cs_cz")

	/** Welsh */
	val cy_gb : Lang = Lang("cy_gb")

	/** Danish */
	val da_dk : Lang = Lang("da_dk")

	/** Austrian German */
	val de_at : Lang = Lang("de_at")

	/** Swiss German */
	val de_ch : Lang = Lang("de_ch")

	/** German */
	val de_de : Lang = Lang("de_de")

	/** Greek */
	val el_gr : Lang = Lang("el_gr")

	/** Australian English */
	val en_au : Lang = Lang("en_au")

	/** Canadian English */
	val en_ca : Lang = Lang("en_ca")

	/** South African English */
	val en_za : Lang = Lang("en_za")

	/** British English */
	val en_gb : Lang = Lang("en_gb")

	/** New Zealand English */
	val en_nz : Lang = Lang("en_nz")

	/** Pirate English */
	val en_7s : Lang = Lang("en_7s")

	/** British English (upside down) */
	val en_ud : Lang = Lang("en_ud")

	/** American English */
	val en_us : Lang = Lang("en_us")

	/** English puristic */
	val enp   : Lang = Lang("enp")

	/** Early Modern English (Wikipedia) */
	val en_ws : Lang = Lang("en_ws")

	/** Esperanto */
	val eo_uy : Lang = Lang("eo_uy")

	/** Argentinian Spanish */
	val es_ar : Lang = Lang("es_ar")

	/** Chilean Spanish */
	val es_CL : Lang = Lang("es_CL")

	/** Spanish */
	val es_es : Lang = Lang("es_es")

	/** Mexican Spanish */
	val es_mx : Lang = Lang("es_mx")

	/** Uruguayan Spanish */
	val es_uy : Lang = Lang("es_uy")

	/** Venezuelan Spanish */
	val es_ve : Lang = Lang("es_ve")

	/** Estonian */
	val et_ee : Lang = Lang("et_ee")

	/** Basque */
	val eu_es : Lang = Lang("eu_es")

	/** Persian */
	val fa_ir : Lang = Lang("fa_ir")

	/** Finnish */
	val fi_fi : Lang = Lang("fi_fi")

	/** Filipino */
	val fil_ph: Lang = Lang("fil_ph")

	/** Faroese */
	val fo_fo : Lang = Lang("fo_fo")

	/** Canadian French */
	val fr_ca : Lang = Lang("fr_ca")

	/** French */
	val fr_fr : Lang = Lang("fr_fr")

	/** Frisian */
	val fy_nl : Lang = Lang("fy_nl")

	/** Irish */
	val ga_ie : Lang = Lang("ga_ie")

	/** Scottish Gaelic */
	val gd_gb : Lang = Lang("gd_gb")

	/** Galician */
	val gl_es : Lang = Lang("gl_es")

	/** Manx */
	val gv_im : Lang = Lang("gv_im")

	/** Hawaiian */
	val haw: Lang = Lang("haw")

	/** Hebrew */
	val he_il: Lang = Lang("he_il")

	/** Hindi */
	val hi_in: Lang = Lang("hr_hr")

	/** Croatian */
	val hr_hr: Lang = Lang("hr_hr")

	/** Hungarian */
	val hu_hu: Lang = Lang("hu_hu")

	/** Armenian */
	val hy_am: Lang = Lang("hy_am")

	/** Indonesian */
	val id_id: Lang = Lang("id_id")

	/** Interslavic */
	val isv: Lang = Lang("isv")

	/** Igbo */
	val ig_ng: Lang = Lang("ig_ng")

	/** Ido */
	val io_en: Lang = Lang("io_en")

	/** Icelandic */
	val is_is: Lang = Lang("is_is")

	/** Italian */
	val it_it: Lang = Lang("it_it")

	/** Japanese */
	val ja_jp: Lang = Lang("ja_jp")

	/** Lojban */
	val jbo: Lang = Lang("jbo")

	/** Georgian */
	val ka_ge: Lang = Lang("ka_ge")

	/** Kabyle */
	val kab_dz: Lang = Lang("kab_dz")

	/** Kannada */
	val kn_in: Lang = Lang("kn_in")

	/** Korean */
	val ko_kr: Lang = Lang("ko_kr")

	/** Kölsch/Ripuarian */
	val ksh_de: Lang = Lang("ksh_de")

	/** Cornish */
	val kw_gb: Lang = Lang("kw_gb")

	/** Latin */
	val la_va: Lang = Lang("la_va")

	/** Luxembourgish */
	val lb_lu: Lang = Lang("lb_lu")

	/** Limburgish */
	val li_li: Lang = Lang("li_li")

	/** LOLCAT */
	val lol_aa: Lang = Lang("lol_aa")

	/** Lithuanian */
	val lt_lt: Lang = Lang("lt_lt")

	/** Latvian */
	val lv_lv: Lang = Lang("lv_lv")

	/** Māori */
	val mi_nz: Lang = Lang("mi_nz")

	/** Macedonian */
	val mk_mk: Lang = Lang("mk_mk")

	/** Mongolian */
	val mn_mn: Lang = Lang("mn_mn")

	/** Mohawk */
	val moh_us: Lang = Lang("moh_us")

	/** Malay */
	val ms_my: Lang = Lang("ms_my")

	/** Maltese */
	val mt_mt: Lang = Lang("mt_mt")

	/** Low German */
	val nds_de: Lang = Lang("nds_de")

	/** Dutch, Flemish */
	val nl_be: Lang = Lang("nl_be")

	/** Dutch */
	val nl_nl: Lang = Lang("nl_nl")

	/** Norwegian Nynorsk */
	val nn_no: Lang = Lang("nn_no")

	/** Norwegian */
	val no_no: Lang = Lang("no_no")

	/** Norwegian Bokmål */
	val nb_no: Lang = Lang("nb_no")

	/** Nuu-chah-nulth */
	val nuk: Lang = Lang("nuk")

	/** Occitan */
	val oc_fr: Lang = Lang("oc_fr")

	/** Ojibwe */
	val oj_ca: Lang = Lang("oj_ca")

	/** Elfdalian */
	val ovd_se: Lang = Lang("ovd_se")

	/** Polish */
	val pl_pl: Lang = Lang("pl_pl")

	/** Brazilian Portuguese */
	val pt_br: Lang = Lang("pt_br")

	/** Portuguese */
	val pt_pt: Lang = Lang("pt_pt")

	/** Quenya (Form of Elvish from LOTR) */
	val qya_aa: Lang = Lang("qya_aa")

	/** Romanian */
	val ro_ro: Lang = Lang("ro_ro")

	/** Russian */
	val ru_ru: Lang = Lang("ru_ru")

	/** Northern*/
	val sme: Lang = Lang("sme")

	/** Slovak */
	val sk_sk: Lang = Lang("sk_sk")

	/** Slovenian */
	val sl_si: Lang = Lang("sl_si")

	/** Somali */
	val so_so: Lang = Lang("so_so")

	/** Albanian */
	val sq_al: Lang = Lang("sq_al")

	/** Serbian */
	val sr_sp: Lang = Lang("sr_sp")

	/** Swedish */
	val sv_se: Lang = Lang("sv_se")

	/** Allgovian German */
	val swg: Lang = Lang("swg")

	/** Upper Saxon German */
	val sxu: Lang = Lang("sxu")

	/** Silesian */
	val szl: Lang = Lang("szl")

	/** Tamil */
	val ta_IN: Lang = Lang("ta_IN")

	/** Thai */
	val th_th: Lang = Lang("th_th")

	/** Klingon */
	val tlh_aa: Lang = Lang("tlh_aa")

	/** Turkish */
	val tr_tr: Lang = Lang("tr_tr")

	/** Tatar */
	val tt_ru: Lang = Lang("tt_ru")

	/** Talossan */
	val tzl_tzl: Lang = Lang("tzl_tzl")

	/** Ukrainian */
	val uk_ua: Lang = Lang("uk_ua")

	/** Valencian */
	val `ca-val_es`: Lang = Lang("ca-val_es")

	/** Venetian */
	val vec_it: Lang = Lang("vec_it")

	/** Vietnamese */
	val vi_vn: Lang = Lang("vi_vn")

	/** Franconian */
	val vmf_de: Lang = Lang("vmf_de")

	/** Yoruba */
	val yo_ng: Lang = Lang("yo_ng")

	/** Chinese (Simplified) */
	val zh_cn: Lang = Lang("zh_cn")

	/** Chinese (Traditional) */
	val zh_tw: Lang = Lang("zh_tw")
}