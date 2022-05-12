package repository;

import dtos.AssetsDTO;
import dtos.MountDTO;
import dtos.ResponseBodyDTO;
import utils.types.Mount;

import javax.print.attribute.standard.Media;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Set;

public interface IMountRepo {

    Set<MountDTO> getAllMounts() throws IOException, URISyntaxException;
    MountDTO getMountByMountId(int id) throws IOException, URISyntaxException;
    AssetsDTO getCreatureMediaByMountId(int id);
    AssetsDTO getCreatureMediaByCreatureId(int id);
    Set<AssetsDTO> getItemMediaByItemId(int id) throws IOException, URISyntaxException;
    Set<AssetsDTO> getItemMediaByMountId(int id) throws IOException, URISyntaxException;
}
