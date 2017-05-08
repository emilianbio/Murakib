package forms;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
@Entity
@Table(name = "grub", schema = "public")
public class Grub implements java.io.Serializable {
	private static final long serialVersionUID = 4418029727139184238L;
	@Id
    @Column(name = "id",nullable=false)		
	@SequenceGenerator(name="tabloSequnce", sequenceName="grub_id_seq")	
	@GeneratedValue(generator="tabloSequnce")
	private long id;
	@Column(name="isim")
	private String isim;
	@Column(name="baslama_tarihi") 
	@DateTimeFormat(pattern="dd.MM.yyyy")
	private Date baslamaTarihi;
	@Column(name="bitis_tarihi") 
	@DateTimeFormat(pattern="dd.MM.yyyy")
	private Date bitisTarihi;
	@Column(name="parca_suresi")
	private Short parcaSuresi;
	@ManyToOne
	@JoinColumn(name="grub_id")
    private Grub grub;
	@ManyToOne
	@JoinColumn(name="evrad_bolumleme_id")
    private EvradBolumleme evradBolumleme;
	@Column(name="sabit")
	private Boolean sabit;
	public Grub(){
		
	}
	public long getId() {
		return id;
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
	
	public Date getBaslamaTarihi() {
		return baslamaTarihi;
	}
	public void setBaslamaTarihi(Date baslamaTarihi) {
		this.baslamaTarihi = baslamaTarihi;
	}
	public Grub getGrub() {
		return grub;
	}
	public void setGrub(Grub grub) {
		this.grub = grub;
	}
	public EvradBolumleme getEvradBolumleme() {
		return evradBolumleme;
	}
	public void setEvradBolumleme(EvradBolumleme evradBolumleme) {
		this.evradBolumleme = evradBolumleme;
	}
	public Date getBitisTarihi() {
		return bitisTarihi;
	}
	public void setBitisTarihi(Date bitisTarihi) {
		this.bitisTarihi = bitisTarihi;
	}
	public Short getParcaSuresi() {
		return parcaSuresi;
	}
	public void setParcaSuresi(Short parcaSuresi) {
		this.parcaSuresi = parcaSuresi;
	}
	public Boolean getSabit() {
		return sabit;
	}
	public void setSabit(Boolean sabit) {
		this.sabit = sabit;
	}
	
}