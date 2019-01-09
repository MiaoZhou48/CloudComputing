package NginxServer.entity;

public class Standard {
    private String ipAddress;//当前服务器的IP地址
    private double cpuURate;//CPU利用率
    private double ramURate;//RAM使用率
    private double diskURate;//磁盘使用率
    private double netURate;//网络带宽使用率

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public double getCpuURate() {
        return cpuURate;
    }

    public void setCpuURate(double cpuURate) {
        this.cpuURate = cpuURate;
    }

    public double getRamURate() {
        return ramURate;
    }

    public void setRamURate(double ramURate) {
        this.ramURate = ramURate;
    }

    public double getDiskURate() {
        return diskURate;
    }

    public void setDiskURate(double diskURate) {
        this.diskURate = diskURate;
    }

    public double getNetURate() {
        return netURate;
    }

    public void setNetURate(double netURate) {
        this.netURate = netURate;
    }

    @Override
    public String toString() {
        return "Standard{" +
                "ipAddress='" + ipAddress + '\'' +
                ", cpuURate=" + cpuURate +
                ", ramURate=" + ramURate +
                ", diskURate=" + diskURate +
                ", netURate=" + netURate +
                '}';
    }
}
