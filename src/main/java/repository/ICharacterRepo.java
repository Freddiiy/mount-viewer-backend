package repository;

import utils.types.Mount;

import javax.print.attribute.standard.Media;
import java.util.Set;

public interface ICharacterRepo {
    Character getCharacterInfo(String region, String serverSlug, String name);
    Set<Mount> getCharacterMounts(String region, String serverSlug, String name);
    Set<Mount> getCharacterMountsByCharacterId(int id);
    Media getMediaByCharacterId(int id);
}
