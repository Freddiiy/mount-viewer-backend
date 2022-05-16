package repository;

import dtos.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Set;

public interface IMountRepo {

    List<ExtendedMountDTO> getAllMounts() throws IOException, URISyntaxException;
    MountDTO getMountByMountId(Long id) throws IOException, URISyntaxException;
    Set<AssetsDTO> getCreatureMediaByMountId(Long id) throws IOException, URISyntaxException;
    Set<AssetsDTO> getCreatureMediaByCreatureId(Long id) throws IOException, URISyntaxException;
    Set<AssetsDTO> getItemMediaByItemId(Long id) throws IOException, URISyntaxException;
    Set<AssetsDTO> getItemMediaByMountId(Long id) throws IOException, URISyntaxException;
    String getSourceByMountId(Long id) throws IOException, URISyntaxException;
    String getDescriptionByMountId(Long id) throws IOException, URISyntaxException;
    void fillItemDisplay() throws IOException, URISyntaxException;
}
