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
@Table(name = "evrad_okuma", schema = "public")
public class EvradOkuma implements java.io.Serializable {
	private static final long serialVersionUID = 4418029727139184238L;
	@Id
    @Column(name = "id",nullable=false)		
	@SequenceGenerator(name="tabloSequnce", sequenceName="evrad_okuma_id_seq")	
	@GeneratedValue(generator="tabloSequnce")
	private long id;
	@Column(name="tarih") 
	@DateTimeFormat(pattern="dd.MM.yyyy")
	private Date tarih;
	@Column(name="kayit_tarihi")
	private Date kayitTarihi;
	@Column(name="aciklama")
	private String aciklama;
	
	@ManyToOne
	@JoinColumn(name="evrad_abone_id")
    private EvradAbone evradAbone;
	@ManyToOne
	@JoinColumn(name="evrad_kisim_id")
    private EvradKisim evradKisim;
	@ManyToOne
	@JoinColumn(name="kaydeden_kullanici_id")
    private Kullanici kaydedenKullanici;
	
	public EvradOkuma() {
		
	}

	public EvradOkuma(long id) {
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

	public String getAciklama() {
		return aciklama;
	}

	public void setAciklama(String aciklama) {
		this.aciklama = aciklama;
	}

	public EvradAbone getEvradAbone() {
		return evradAbone;
	}

	public void setEvradAbone(EvradAbone evradAbone) {
		this.evradAbone = evradAbone;
	}

	public EvradKisim getEvradKisim() {
		return evradKisim;
	}

	public void setEvradKisim(EvradKisim evradKisim) {
		this.evradKisim = evradKisim;
	}

}
