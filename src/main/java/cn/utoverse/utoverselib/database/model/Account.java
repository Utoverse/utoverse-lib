package cn.utoverse.utoverselib.database.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "account")
public class Account {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private int name;

}
