package com.softteco.toolset.files;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author serge
 */
public abstract class FileUploadServlet extends HttpServlet {

    protected abstract String getFolder();

    public File getFile(final String uuid) {
        return new File(getFolder() + "/" + uuid);
    }

    public String buildFileName(final FileItem fileItem) {
        return UUID.randomUUID().toString();
    }

    public final String getFileName(final FileItem fileItem) {
        final String fileName = buildFileName(fileItem);
        if (fileName.contains("..") || fileName.contains("/")) {
            throw new RuntimeException("Incorrect name of file: " + fileName);
        }
        return fileName;
    }

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("FILE UPLOAD!!!");
        
        String fileName = null;
        try {
            final FileItemFactory fileItemFactory = new DiskFileItemFactory(1000000, new File(getFolder()));
            final List<FileItem> items = new ServletFileUpload(fileItemFactory).parseRequest(req);
            System.out.println("received POST with " + items.size() + " items.");

            for (FileItem item : items) {
                if (item.isFormField()) {
                    continue;
                }

                try {
                    final File file = getFile(fileName = getFileName(item));
                    item.write(file);
                    fileUploaded(item, file, req);
                } catch (Exception e) {
                    e.printStackTrace(System.out);
                }
            }
        } catch (FileUploadException e) {
            throw new ServletException(e);
        }

        if (getRedirect() != null) {
            resp.sendRedirect(req.getContextPath() + getRedirect() + "?uuid=" + fileName);
        }
    }

    protected void fileUploaded(final FileItem item, final File file, final HttpServletRequest request) {
    }

    protected abstract String getRedirect();
}
