package forms;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
@Entity
@Table(name = "evrad_bolumleme", schema = "public")
public class EvradBolumleme implements java.io.Serializable {
	private static final long serialVersionUID = 4418029727139184238L;
	@Id
    @Column(name = "id",nullable=false)		
	@SequenceGenerator(name="tabloSequnce", sequenceName="evrad_bolumleme_id_seq")	
	@GeneratedValue(generator="tabloSequnce")
	private long id;
	@Column(name="isim")
	private String isim;
	@Column(name="parca_sayisi")
	private Short parcaSayisi;
				
	public EvradBolumleme() {
	}
	public EvradBolumleme(Long id) {
		this.id = id;
	}

	public long getId() {
	return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}
	public String getIsim() {
		return isim;
	}
	public void setIsim(String isim) {
		this.isim = isim;
	}
	public Short getParcaSayisi() {
		return parcaSayisi;
	}
	public void setParcaSayisi(Short parcaSayisi) {
		this.parcaSayisi = parcaSayisi;
	}
	
	}
