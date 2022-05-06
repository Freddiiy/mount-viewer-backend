package repository;

import utils.types.Mount;

import javax.persistence.EntityManagerFactory;
import javax.print.attribute.standard.Media;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Set;

public interface IMountRepo {

    Set<Mount> getAllMounts() throws IOException, URISyntaxException;
    Mount getMountByMountId(int id);
    Mount getMountByName(String name);
    Mount getMountByItemId(int itemId);
    Media getCreatureMediaByMountId(int id);
    Media getCreatureMediaByCreatureId(int id);
    Media getItemMediaByItemId(int id);
    Media getItemMediaByMountId(int id);
}
