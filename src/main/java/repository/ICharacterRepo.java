package repository;

import dtos.CharacterDTO;
import utils.types.Mount;

import javax.print.attribute.standard.Media;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Set;

public interface ICharacterRepo {
    CharacterDTO getCharacterInfo(String region, String serverSlug, String name) throws IOException, URISyntaxException;
    Set<Mount> getCharacterMounts(String region, String serverSlug, String name);
    Set<Mount> getCharacterMountsByCharacterId(int id);
    Media getMediaByCharacterId(int id);
}
