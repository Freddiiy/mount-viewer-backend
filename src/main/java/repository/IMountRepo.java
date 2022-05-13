package repository;

import dtos.AssetsDTO;
import dtos.MountDTO;
import dtos.MountElementDTO;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Set;

public interface IMountRepo {

    Set<MountDTO> getAllMounts() throws IOException, URISyntaxException;
    MountDTO getMountByMountId(Long id) throws IOException, URISyntaxException;
    Set<AssetsDTO> getCreatureMediaByMountId(Long id) throws IOException, URISyntaxException;
    Set<AssetsDTO> getCreatureMediaByCreatureId(Long id) throws IOException, URISyntaxException;
    Set<AssetsDTO> getItemMediaByItemId(Long id) throws IOException, URISyntaxException;
    Set<AssetsDTO> getItemMediaByMountId(Long id) throws IOException, URISyntaxException;
    String getSourceByMountId(Long id) throws IOException, URISyntaxException;
    String getDescriptionByMountId(Long id) throws IOException, URISyntaxException;
}
