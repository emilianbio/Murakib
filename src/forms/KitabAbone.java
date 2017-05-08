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
@Table(name = "kitab_abone", schema = "public")
public class KitabAbone implements java.io.Serializable {
	private static final long serialVersionUID = 4418029727139184238L;
	@Id
    @Column(name = "id",nullable=false)		
	@SequenceGenerator(name="tabloSequnce", sequenceName="kitab_abone_id_seq")	
	@GeneratedValue(generator="tabloSequnce")
	private long id;
	@Column(name="kayit_tarihi")
	private Date kayitTarihi;
	@Column(name="baslama_tarihi") 
	@DateTimeFormat(pattern="dd.MM.yyyy")
	private Date baslamaTarihi;
	@Column(name="pasif")
	private Boolean pasif;
	@Column(name="gunluk_sayfa_sayisi")
	private Short gunlukSayfaSayisi;
	@Column(name="aciklama")
	private String aciklama;
	@Column(name="hususi")
	private Boolean hususi;
	@ManyToOne
	@JoinColumn(name="kullanici_id")
    private Kullanici kullanici;
	@ManyToOne
	@JoinColumn(name="kaydeden_kullanici_id")
    private Kullanici kaydedenKullanici;
	
	
	public KitabAbone() {
	}

	public KitabAbone(long id) {
		this.id = id;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Kullanici getKullanici() {
		return kullanici;
	}

	public void setKullanici(Kullanici kullanici) {
		this.kullanici = kullanici;
	}

	public Date getKayitTarihi() {
		return kayitTarihi;
	}

	public void setKayitTarihi(Date kayitTarihi) {
		this.kayitTarihi = kayitTarihi;
	}

	public Date getBaslamaTarihi() {
		return baslamaTarihi;
	}

	public void setBaslamaTarihi(Date baslamaTarihi) {
		this.baslamaTarihi = baslamaTarihi;
	}

	public Kullanici getKaydedenKullanici() {
		return kaydedenKullanici;
	}

	public void setKaydedenKullanici(Kullanici kaydedenKullanici) {
		this.kaydedenKullanici = kaydedenKullanici;
	}

	public Boolean getPasif() {
		return pasif;
	}

	public void setPasif(Boolean pasif) {
		this.pasif = pasif;
	}

	public Short getGunlukSayfaSayisi() {
		return gunlukSayfaSayisi;
	}

	public void setGunlukSayfaSayisi(Short gunlukSayfaSayisi) {
		this.gunlukSayfaSayisi = gunlukSayfaSayisi;
	}

	public String getAciklama() {
		return aciklama;
	}

	public void setAciklama(String aciklama) {
		this.aciklama = aciklama;
	}

	public Boolean getHususi() {
		return hususi;
	}

	public void setHususi(Boolean hususi) {
		this.hususi = hususi;
	}	
	
}
