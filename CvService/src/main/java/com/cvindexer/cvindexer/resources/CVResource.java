package com.cvindexer.cvindexer.resources;
import com.cvindexer.cvindexer.models.cv;
import com.cvindexer.cvindexer.helpers.CVContent;
import com.cvindexer.cvindexer.services.CVServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@RestController
@RequestMapping("/v1/api/cvindexer")
public class CVResource {

    private String fileBasePath = "/Users/mylenebalech/Documents/CVIndexer/CvService/cvs/";
    private String contenu = "";

    @Autowired
    private CVContent cvContent ;

    @Autowired
    private CVServices services;

    /**
     * Permet d'enregistrer un nouveau cv : http://localhost:8080/v1/api/cvindexer/upload
     * @param file fichier envoyer par formulaire
     * @return la réponse
     * @throws IOException
     */
    @PostMapping("/upload")
    public ResponseEntity uploadToLocalFileSystem(@RequestParam("file") MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Path path = Paths.get(fileBasePath + fileName);
        try {
            // téléchargement du fichier
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            //récupération contenu fichier
            contenu = cvContent.ReadFile(fileBasePath + fileName, file.getContentType());
        }
        catch (IOException e){
            e.printStackTrace();
        }
        cv cv = new cv(); // instanciation nouveau cv
        cv.setFilename(fileName); // affectation filename
        cv.setContent(contenu); // affectation contenu
        return ResponseEntity.created(URI.create("/v1/api/cvindexer")).body(services.insertCv(cv));
    }

    /**
     * Retourne tous les cvs : http://localhost:8080/v1/api/cvindexer/allCV
     * @return la réponse
     * @throws IOException
     */
    @GetMapping("/allCV")
    public ResponseEntity<List<cv>> getCV() throws IOException {
        return ResponseEntity.ok(services.getAllCV());
    }

    /**
     * Retourne les résultats de la recherche
     * @param research
     * @return
     * @throws IOException
     */
    @GetMapping("/research")
    public ResponseEntity getResearch(@RequestParam("research") String research) throws IOException{
        return ResponseEntity.ok(services.getResearch(research));
    }

    /**
     * Page d'accueil
     * @return
     */
    @RequestMapping("/")
    public ResponseEntity<String> welcome() {
        String string = "{ \"welcome\" : \"Bienvenue sur la CVThèque\"}";
        return ResponseEntity.ok(string);
    }

}
