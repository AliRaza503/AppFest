class model {
    private var AyatId: Int? = null
    private var AyatNo: Int? = null
    private var SuraNo: Int? = null
    private var Arabic: String? = null
    private var Urdu1: String? = null
    private var Urdu2: String? = null
    private var Eng1: String? = null
    private var Eng2: String? = null
    private var RakuId: Int? = null
    private var PRakuId: Int? = null
    private var ParahId: Int? = null


    constructor(AyatId:Int,AyatNo:Int,SuraNo:Int,Arabic:String,Urdu1:String,Urdu2:String,Eng1:String,Eng2:String,RakuId:Int,PRakuId:Int,ParahId:Int){
        this.AyatId = AyatId;
        this.AyatNo = AyatNo;
        this.SuraNo = SuraNo;
        this.Arabic = Arabic;
        this.Eng1=Eng1;
        this.Eng2=Eng2;
        this.Urdu1=Urdu1;
        this.Urdu2=Urdu2;
        this.PRakuId=PRakuId;
        this.ParahId=ParahId;
        this.PRakuId=PRakuId;
    }

    fun getFirst_name(): String? {
        return first_name
    }

    fun setFirst_name(first_name: String?) {
        this.first_name = first_name
    }