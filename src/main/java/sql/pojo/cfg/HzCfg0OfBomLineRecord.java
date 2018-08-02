package sql.pojo.cfg;

/**
 * 配置值与BOM行的映射关系
 */
public class HzCfg0OfBomLineRecord {
    /**
     * 主键
     */
    private String puid;
    /**
     * 对应的BOM行的PUID
     */
    private String pBomlinepuid;
    /**
     * 配置名
     */
    private String pCfg0name;
    /**
     * 族名
     */
    private String pCfg0familyname;
    /**
     * 数模层的PUID
     */
    private String pBomDigifaxId;
    /**
     * BOMLine行名
     */
    private String pBomLineName;
    /**
     * 指向选项值的puid，做为外键
     */
    private String pToCfg0IdOfBl;

    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid == null ? null : puid.trim();
    }

    public String getpBomlinepuid() {
        return pBomlinepuid;
    }

    public void setpBomlinepuid(String pBomlinepuid) {
        this.pBomlinepuid = pBomlinepuid == null ? null : pBomlinepuid.trim();
    }

    public String getpCfg0name() {
        return pCfg0name;
    }

    public void setpCfg0name(String pCfg0name) {
        this.pCfg0name = pCfg0name;
    }

    public String getpCfg0familyname() {
        return pCfg0familyname;
    }

    public void setpCfg0familyname(String pCfg0familyname) {
        this.pCfg0familyname = pCfg0familyname;
    }

    public String getpBomDigifaxId() {
        return pBomDigifaxId;
    }

    public void setpBomDigifaxId(String pBomDigifaxId) {
        this.pBomDigifaxId = pBomDigifaxId == null ? null : pBomDigifaxId.trim();
    }

    public String getpBomLineName() {
        return pBomLineName;
    }

    public void setpBomLineName(String pBomLineName) {
        this.pBomLineName = pBomLineName == null ? null : pBomLineName.trim();
    }

    public String getpToCfg0IdOfBl() {
        return pToCfg0IdOfBl;
    }

    public void setpToCfg0IdOfBl(String pToCfg0IdOfBl) {
        this.pToCfg0IdOfBl = pToCfg0IdOfBl == null ? null : pToCfg0IdOfBl.trim();
    }
}