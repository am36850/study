package com.project.batch.springbatchdateconversion.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "bse_historical_data", catalog = "share_market")
public class BseHistoricalData implements java.io.Serializable {

    private Integer id;
    private String scCode;
    private String scName;
    private String scGroup;
    private String scType;
    private BigDecimal open;
    private BigDecimal high;
    private BigDecimal low;
    private BigDecimal close;
    private BigDecimal last;
    private BigDecimal prevclose;
    private int noTrades;
    private int noOfShrs;
    private BigDecimal netTurnov;
    private String tdcloindi;
    private String isinCode;
    private Date tradingDate;
    private String filer1;
    private String filer2;

    public BseHistoricalData() {
    }

    public BseHistoricalData(String scCode, String scName, String scGroup, String scType, BigDecimal open, BigDecimal low, BigDecimal close, BigDecimal last, BigDecimal prevclose,
            int noTrades, int noOfShrs, BigDecimal netTurnov, String isinCode, Date tradingDate) {
        this.scCode = scCode;
        this.scName = scName;
        this.scGroup = scGroup;
        this.scType = scType;
        this.open = open;
        this.low = low;
        this.close = close;
        this.last = last;
        this.prevclose = prevclose;
        this.noTrades = noTrades;
        this.noOfShrs = noOfShrs;
        this.netTurnov = netTurnov;
        this.isinCode = isinCode;
        this.tradingDate = tradingDate;
    }

    public BseHistoricalData(String scCode, String scName, String scGroup, String scType, BigDecimal open, BigDecimal high, BigDecimal low, BigDecimal close, BigDecimal last,
            BigDecimal prevclose, int noTrades, int noOfShrs, BigDecimal netTurnov, String tdcloindi, String isinCode, Date tradingDate, String filer1, String filer2) {
        this.scCode = scCode;
        this.scName = scName;
        this.scGroup = scGroup;
        this.scType = scType;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.last = last;
        this.prevclose = prevclose;
        this.noTrades = noTrades;
        this.noOfShrs = noOfShrs;
        this.netTurnov = netTurnov;
        this.tdcloindi = tdcloindi;
        this.isinCode = isinCode;
        this.tradingDate = tradingDate;
        this.filer1 = filer1;
        this.filer2 = filer2;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)

    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "SC_CODE", nullable = false, length = 100)
    public String getScCode() {
        return this.scCode;
    }

    public void setScCode(String scCode) {
        this.scCode = scCode;
    }

    @Column(name = "SC_NAME", nullable = false, length = 500)
    public String getScName() {
        return this.scName;
    }

    public void setScName(String scName) {
        this.scName = scName;
    }

    @Column(name = "SC_GROUP", nullable = false, length = 5)
    public String getScGroup() {
        return this.scGroup;
    }

    public void setScGroup(String scGroup) {
        this.scGroup = scGroup;
    }

    @Column(name = "SC_TYPE", nullable = false, length = 5)
    public String getScType() {
        return this.scType;
    }

    public void setScType(String scType) {
        this.scType = scType;
    }

    @Column(name = "OPEN", nullable = false, precision = 20, scale = 6)
    public BigDecimal getOpen() {
        return this.open;
    }

    public void setOpen(BigDecimal open) {
        this.open = open;
    }

    @Column(name = "HIGH", precision = 20, scale = 6)
    public BigDecimal getHigh() {
        return this.high;
    }

    public void setHigh(BigDecimal high) {
        this.high = high;
    }

    @Column(name = "LOW", nullable = false, precision = 20, scale = 6)
    public BigDecimal getLow() {
        return this.low;
    }

    public void setLow(BigDecimal low) {
        this.low = low;
    }

    @Column(name = "CLOSE", nullable = false, precision = 20, scale = 6)
    public BigDecimal getClose() {
        return this.close;
    }

    public void setClose(BigDecimal close) {
        this.close = close;
    }

    @Column(name = "LAST", nullable = false, precision = 20, scale = 6)
    public BigDecimal getLast() {
        return this.last;
    }

    public void setLast(BigDecimal last) {
        this.last = last;
    }

    @Column(name = "PREVCLOSE", nullable = false, precision = 20, scale = 6)
    public BigDecimal getPrevclose() {
        return this.prevclose;
    }

    public void setPrevclose(BigDecimal prevclose) {
        this.prevclose = prevclose;
    }

    @Column(name = "NO_TRADES", nullable = false)
    public int getNoTrades() {
        return this.noTrades;
    }

    public void setNoTrades(int noTrades) {
        this.noTrades = noTrades;
    }

    @Column(name = "NO_OF_SHRS", nullable = false)
    public int getNoOfShrs() {
        return this.noOfShrs;
    }

    public void setNoOfShrs(int noOfShrs) {
        this.noOfShrs = noOfShrs;
    }

    @Column(name = "NET_TURNOV", nullable = false, precision = 24, scale = 4)
    public BigDecimal getNetTurnov() {
        return this.netTurnov;
    }

    public void setNetTurnov(BigDecimal netTurnov) {
        this.netTurnov = netTurnov;
    }

    @Column(name = "TDCLOINDI", length = 100)
    public String getTdcloindi() {
        return this.tdcloindi;
    }

    public void setTdcloindi(String tdcloindi) {
        this.tdcloindi = tdcloindi;
    }

    @Column(name = "ISIN_CODE", nullable = false, length = 15)
    public String getIsinCode() {
        return this.isinCode;
    }

    public void setIsinCode(String isinCode) {
        this.isinCode = isinCode;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "TRADING_DATE", nullable = false, length = 10)
    public Date getTradingDate() {
        return this.tradingDate;
    }

    public void setTradingDate(Date tradingDate) {
        this.tradingDate = tradingDate;
    }

    @Column(name = "filer1", length = 50)
    public String getFiler1() {
        return this.filer1;
    }

    public void setFiler1(String filer1) {
        this.filer1 = filer1;
    }

    @Column(name = "filer2", length = 50)
    public String getFiler2() {
        return this.filer2;
    }

    public void setFiler2(String filer2) {
        this.filer2 = filer2;
    }

}
