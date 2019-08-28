package vip.hoody.wechat.domain.user

/**
 * User tag
 */
class Tag {

    private String id
    private String name
    private long count

    Tag(String name) {
        this.name = name
    }

    Tag(String id, String name) {
        this.id = id
        this.name = name
    }

    Tag(String id, String name, long count) {
        this.id = id
        this.name = name
        this.count = count
    }

    long getCount() {
        return count
    }

    void setCount(long count) {
        this.count = count
    }

    String getId() {
        return id
    }

    void setId(String id) {
        this.id = id
    }

    String getName() {
        return name
    }

    void setName(String name) {
        this.name = name
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", count=" + count +
                '}';
    }
}
