TrashCssView
============

Scans your site for css styles that are not used on pages.
TrashCssView
============

Scans your site for css styles that are not used on pages.

Make sure you have: 
1. Java
2. maven (if you build from sources)

Usage (for UNIX systems):
1. sudo sh ./show_trash_in_css.sh {page-url} {optional-parameters}

{page-url} example: http://vk.com
{optional-parameters}:
  -t: defines scan type. Posible values: 0, 1. 0 - partial scan, 1 - full scan. 
  -d: defines scan depth: Posible values: 0,1,...,n, where n - number > 0. 

Example:
  Scan one page: 
    sudo sh ./show_trash_in_css.sh http://vk.com
    or 
    sudo sh ./show_trash_in_css.sh http://vk.com -t 0 -d 0 
  Scan 1 level deep:
    sudo sh ./show_trash_in_css.sh http://vk.com -t 0 -d 1
  Scan full site:
    sudo sh ./show_trash_in_css.sh http://vk.com -t 1

Usage (for Windows):
1. cd target
2. java -jar TrashCssView-0.0.1-SNAPSHOT-jar-with-dependencies.jar {page-url} {optional-parameters}
