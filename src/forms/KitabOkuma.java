package forms;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
@Entity
@Table(name = "kitab_okuma", schema = "public")
public class KitabOkuma implements java.io.Serializable {
	private static final long serialVersionUID = 4418029727139184238L;
	@Id
    @Column(name = "id",nullable=false)		
	@SequenceGenerator(name="tabloSequnce", sequenceName="kitab_okuma_id_seq")	
	@GeneratedValue(generator="tabloSequnce")
	private long id;
	@Column(name="kayit_tarihi")
	private Date kayitTarihi;
	@Column(name="tarih") 
	@DateTimeFormat(pattern="dd.MM.yyyy")
	private Date tarih;
	@Column(name="ilk_sayfa")
	private Short ilkSayfa;
	@Column(name="son_sayfa")
	private Short sonSayfa;
	@Column(name="kitab")
    private String kitab;
	@ManyToOne
	@JoinColumn(name="kaydeden_kullanici_id")
    private Kullanici kaydedenKullanici;
	@ManyToOne
	@JoinColumn(name="kitab_abone_id")
    private KitabAbone kitabAbone;
	
	public KitabOkuma() {
		
	}

	public KitabOkuma(long id) {
		this.id = id;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getKayitTarihi() {
		return kayitTarihi;
	}

	public void setKayitTarihi(Date kayitTarihi) {
		this.kayitTarihi = kayitTarihi;
	}

	public Kullanici getKaydedenKullanici() {
		return kaydedenKullanici;
	}

	public void setKaydedenKullanici(Kullanici kaydedenKullanici) {
		this.kaydedenKullanici = kaydedenKullanici;
	}

	public Date getTarih() {
		return tarih;
	}

	public void setTarih(Date tarih) {
		this.tarih = tarih;
	}

	public Short getIlkSayfa() {
		return ilkSayfa;
	}

	public void setIlkSayfa(Short ilkSayfa) {
		this.ilkSayfa = ilkSayfa;
	}

	public Short getSonSayfa() {
		return sonSayfa;
	}

	public void setSonSayfa(Short sonSayfa) {
		this.sonSayfa = sonSayfa;
	}

	public String getKitab() {
		return kitab;
	}

	public void setKitab(String kitab) {
		this.kitab = kitab;
	}

	public KitabAbone getKitabAbone() {
		return kitabAbone;
	}

	public void setKitabAbone(KitabAbone kitabAbone) {
		this.kitabAbone = kitabAbone;
	}
	
}
