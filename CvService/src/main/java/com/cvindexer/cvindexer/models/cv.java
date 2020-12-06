package com.cvindexer.cvindexer.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class cv {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String filename;

    private String content;

    /**
     * Change le nom de fichier de l'instance en cours
     * @param filename string nom du fichier
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * Change le contenu de fichier de l'instance en cours
     * @param content string contenu du fichier
     */
    public void setContent(String content) {
        this.content = content;
    }
}
