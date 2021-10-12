// try {
//     BufferedImage map = ImageIO.read(getClass().getResource(path));
//     int[] pixels = new int[map.getWidth() * map.getHeight()];
//     WIDTH = map.getWidth();
//     HEIGHT = map.getHeight();
//     tiles = new Tile [map.getWidth() *  map.getHeight()];
//     map.getRGB(0, 0, map.getWidth(), map.getHeight(),pixels ,0,map.getWidth());

//     for(int xx = 0; xx < map.getWidth(); xx++){
//         for(int yy = 0 ; yy < map.getHeight(); yy++){

//             int pixelAtual = pixels[xx + (yy * map.getWidth())];
//             tiles[xx + (yy*WIDTH)] = new FloorTile(xx*16,yy*16, Tile.TILE_FLOOR);

//             if(pixelAtual == 0xFF000000 ){
//                 //floor
//                 tiles[xx + (yy*WIDTH)] = new FloorTile(xx*16,yy*16, Tile.TILE_FLOOR);
//             }else if( pixelAtual == 0xFFFFFFFF){
//                 //parede
//                 tiles[xx + (yy*WIDTH)] = new WallTile(xx*16,yy*16, Tile.TILE_WALL);
//             }else if(pixelAtual == 0xFF0094FF){
//                 //player
//                 Game.player.setX(xx*16);
//                 Game.player.setY(yy*16);


//             }else if(pixelAtual == 0xFFFF0000){
//                 //ENEMY
//                 Enemy en = new Enemy(xx * 16, yy *16, 16,16,Entity.ENEMY_EN);
//                 Game.entities.add(en);
//                 Game.enemy.add(en);
                
//             }else if(pixelAtual == 0xFFFFE97F){
//                 //Weapon
//                 Game.entities.add(new Weapon(xx * 16, yy *16, 16,16,Entity.WEAPONPACK_EN));

//             }else if(pixelAtual == 0xFF00FF21){
//                 //LIFE
//                 Game.entities.add(new LifePack(xx * 16, yy *16, 16,16,Entity.LIFEPACK_EN));

//             }else if(pixelAtual == 0xFF008080){
//                 //Arrow
//                 Game.entities.add(new Arrow(xx * 16, yy *16, 16,16,Entity.ARROW_EN));

//             }
//         }
//     }

// } catch (IOException e) {
//     e.printStackTrace();
// }