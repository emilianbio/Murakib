package forms;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
@Entity
@Table(name = "evrad_kisim", schema = "public")
public class EvradKisim implements java.io.Serializable {
	private static final long serialVersionUID = 4418029727139184238L;
	@Id
    @Column(name = "id",nullable=false)		
	@SequenceGenerator(name="tabloSequnce", sequenceName="evrad_kisim_id_seq")	
	@GeneratedValue(generator="tabloSequnce")
	private long id;
	@Column(name="isim")
	private String isim;
	@Column(name="sayfa_araligi")
	private String sayfaAraligi;
	@Column(name="sira_no")
	private Short siraNo;
	
	@ManyToOne
	@JoinColumn(name="evrad_bolumleme_id")
    private EvradBolumleme evradBolumleme;
	
	public EvradKisim() {
	}
	public EvradKisim(Long id) {
		this.id = id;
	}

	public long getId() {
	return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public String getSayfaAraligi() {
		return sayfaAraligi;
	}
	public void setSayfaAraligi(String sayfaAraligi) {
		this.sayfaAraligi = sayfaAraligi;
	}
	public String getIsim() {
		return isim;
	}
	public void setIsim(String isim) {
		this.isim = isim;
	}
	public Short getSiraNo() {
		return siraNo;
	}
	public void setSiraNo(Short siraNo) {
		this.siraNo = siraNo;
	}
	public EvradBolumleme getEvradBolumleme() {
		return evradBolumleme;
	}
	public void setEvradBolumleme(EvradBolumleme evradBolumleme) {
		this.evradBolumleme = evradBolumleme;
	}
	
	}
