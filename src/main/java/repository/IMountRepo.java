package repository;

import dtos.AssetsDTO;
import dtos.MountDTO;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Set;

public interface IMountRepo {

    Set<MountDTO> getAllMounts() throws IOException, URISyntaxException;
    MountDTO getMountByMountId(int id) throws IOException, URISyntaxException;
    Set<AssetsDTO> getCreatureMediaByMountId(int id) throws IOException, URISyntaxException;
    Set<AssetsDTO> getCreatureMediaByCreatureId(int id) throws IOException, URISyntaxException;
    Set<AssetsDTO> getItemMediaByItemId(int id) throws IOException, URISyntaxException;
    Set<AssetsDTO> getItemMediaByMountId(int id) throws IOException, URISyntaxException;
    String getSourceByMountId(int id) throws IOException, URISyntaxException;
    MountDTO getDescriptionByMountId(int id) throws IOException, URISyntaxException;
}
