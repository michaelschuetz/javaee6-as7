package com.acme.view;
import java.util.List;
import java.util.ArrayList;
import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import com.acme.domain.Room;
import org.jboss.forge.persistence.PersistenceUtil;
import org.jboss.forge.persistence.PaginationHelper;
import org.jboss.seam.transaction.Transactional;
@Transactional @Named @Stateful @RequestScoped public class RoomBean extends PersistenceUtil {
  private List<Room> list=null;
  private Room room=new Room();
  private long id=0;
  private PaginationHelper<Room> pagination;
  public void load(){
    room=findById(Room.class,id);
  }
  public String create(){
    create(room);
    return "view?faces-redirect=true&id=" + room.getId();
  }
  public String delete(){
    delete(room);
    return "list?faces-redirect=true";
  }
  public String save(){
    save(room);
    return "view?faces-redirect=true&id=" + room.getId();
  }
  public long getId(){
    return id;
  }
  public void setId(  long id){
    this.id=id;
    if (id > 0) {
      load();
    }
  }
  public Room getRoom(){
    return room;
  }
  public void setRoom(  Room room){
    this.room=room;
  }
  public List<Room> getList(){
    if (list == null) {
      list=getPagination().createPageDataModel();
    }
    return list;
  }
  public void setList(  List<Room> list){
    this.list=list;
  }
  public PaginationHelper<Room> getPagination(){
    if (pagination == null) {
      pagination=new PaginationHelper<Room>(10){
        @Override public int getItemsCount(){
          return count(Room.class);
        }
        @Override public List<Room> createPageDataModel(){
          return new ArrayList<Room>(findAll(Room.class,getPageFirstItem(),getPageSize()));
        }
      }
;
    }
    return pagination;
  }
  private void recreateModel(){
    list=null;
  }
  public String next(){
    getPagination().nextPage();
    recreateModel();
    return "list";
  }
  public String previous(){
    getPagination().previousPage();
    recreateModel();
    return "list";
  }
}
