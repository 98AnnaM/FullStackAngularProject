-- some test users

INSERT INTO user_roles (id, user_role)
VALUES (1, 'ADMIN'),
       (2, 'USER');

ALTER TABLE user_roles
    ALTER COLUMN id RESTART WITH 3;

INSERT INTO users (id, email, first_name, last_name, image_url, is_active, password)
VALUES (1, 'admin@example.com', 'Admin', 'Adminov', null, 1,
        '57e7759fd2d59275fc3c3cd5dd2ace5013b39ee972999412f3f5f5c3382b6765c2571ef86648abe2'),
       (2, 'user@example.com', 'User', 'Userov', null, 1,
        '57e7759fd2d59275fc3c3cd5dd2ace5013b39ee972999412f3f5f5c3382b6765c2571ef86648abe2');

ALTER TABLE users
    ALTER COLUMN id RESTART WITH 3;

INSERT INTO users_user_roles (user_entity_id, user_roles_id)
VALUES (1, 1),
       (1, 2),
       (2, 2);

INSERT INTO brands (id, name)
VALUES (1, 'Ford'),
       (2, 'Toyota');

ALTER TABLE brands
    ALTER COLUMN id RESTART WITH 3;

INSERT INTO models (id, name, category, start_year, end_year, brand_id, image_url)
VALUES (1, 'Fiesta', 'CAR', 1976, null, 1,
        'https://upload.wikimedia.org/wikipedia/commons/7/7d/2017_Ford_Fiesta_Zetec_Turbo_1.0_Front.jpg'),
       (2, 'Escort', 'CAR', 1968, 2000, 1, 'https://www.auto-data.net/images/f110/Ford-Escort-VII-Hatch-GAL-AFL.jpg'),
       (3, 'Yaris', 'CAR', 1999, null, 2,
        'https://upload.wikimedia.org/wikipedia/commons/thumb/3/3e/2020_Toyota_Yaris_Design_HEV_CVT_1.5_Front.jpg/1280px-2020_Toyota_Yaris_Design_HEV_CVT_1.5_Front.jpg');

ALTER TABLE models
    ALTER COLUMN id RESTART WITH 4;

INSERT INTO offers (id, description, engine, image_url, mileage, price, transmission, manufacture_year, model_id,
                    seller_id, created)
VALUES (1, 'Gold Ford Fiesta.', 'GASOLINE', 'https://imgd-ct.aeplcdn.com/664x415/cw/cars/ford/fiesta.jpg?q=80', 320001,
        11000, 'MANUAL', 2005, 1, 1, '2024-06-10T10:30:00'),
       (2, 'Green Fiesta', 'GASOLINE',
        'https://dizzyriders.bg/uploads/thumbs/gallery/2021-09/f2084ac78384b4fa33e6f8eb84908bb1-620x427.jpg', 320001,
        15000, 'MANUAL', 2005, 1, 2, '2024-06-09T10:30:00'),
       (3, 'Blue Ford Fiesta.', 'GASOLINE',
        'https://automedia.investor.bg//media/files/resized/article/w1200x630/a79/45ab39090a54136f487a05084d77ba79-20-16.jpg',
        320001, 18000, 'MANUAL', 2005, 1, 1, '2024-06-08T10:30:00'),
       (4, 'Grey Fiesta.', 'GASOLINE', 'https://bgauto.eu/image/cache/catalog/cars/ford/fiesta-600x315w.jpg', 320001,
        20000, 'MANUAL', 2005, 1, 2, '2024-06-07T10:30:00'),
       (5, 'Favorite Fiesta.', 'GASOLINE', 'https://imgd-ct.aeplcdn.com/664x415/cw/cars/ford/fiesta.jpg?q=80', 320001,
        2200, 'MANUAL', 2005, 1, 1, '2024-06-06T10:30:00'),
       (6, 'Red Yaris', 'GASOLINE',
        'https://images.ams.bg/images/galleries/183244/toyota-yaris-smeni-pokolenieto-varhu-nova-platforma-1571226605_big.jpg',
        320001, 2601, 'MANUAL', 2005, 1, 2, '2024-05-10T10:30:00'),
       (7, 'Black Yaris', 'GASOLINE',
        'https://kong-proxy-aws.toyota-europe.com/c1-images/resize/ccis/1280x1280/zip/bg/configurationtype/visual-for-grade-selector/product-token/8621c317-62c1-49a3-9ac4-72651e5832dc/grade/a5fc8c53-ea97-4d56-8441-59fb4f86dd02/body/7b77d85b-8f26-4645-82ac-22154a7d6293/fallback/true/padding/50,50,50,50/image-quality/70/day-exterior-4.png',
        320001, 12000, 'MANUAL', 2005, 3, 1, '2024-06-04T10:30:00'),
       (8, 'Blue Yaris', 'GASOLINE', 'https://i.ytimg.com/vi/u0PwgxjS11c/maxresdefault.jpg', 320001, 2601, 'MANUAL',
        2010, 3, 2, '2024-06-03T10:30:00'),
       (9, 'Another Red Yaris.', 'GASOLINE',
        'https://www.carscoops.com/wp-content/uploads/2023/03/2023-Toyota-Yaris-Presto-Thailand-1-1024x768.jpg', 320001,
        23000, 'MANUAL', 2005, 1, 1, '2024-06-02T10:30:00'),
       (10, 'Another black yaris', 'GASOLINE',
        'https://cars.usnews.com/static/images/Auto/izmo/i4483/2014_toyota_yaris_angularfront.jpg', 320001, 16000,
        'MANUAL', 2005, 1, 2, '2024-06-01T10:30:00'),
       (11, 'White Escort', 'GASOLINE',
        'https://automedia.investor.bg/media/files/resized/uploadedfiles/640x0/6f9/2d97459b3d42c1fe66fe5cc87b1e76f9-ford-escort-reestilizado-china-2.jpg',
        320001, 19000, 'MANUAL', 2005, 2, 1, '2024-05-10T10:30:00'),
       (12, 'Gold Escort', 'GASOLINE',
        'https://www.investor.bg/media/files/resized/article/1600x/3bd/e01b107fd82fc35c2d8a144d00a183bd-0000154971-article3.jpg',
        320001, 2601, 'MANUAL', 2005, 2, 2, '2024-05-11T10:30:00'),
       (13, 'Gold Escort', 'GASOLINE', 'https://i.ytimg.com/vi/4N19myFH1gI/hqdefault.jpg', 320001, 13000, 'MANUAL',
        2005, 2, 1, '2024-05-12T10:30:00');

ALTER TABLE offers
    ALTER COLUMN id RESTART WITH 14;

INSERT INTO comments (id, text_content, created, author_id, offer_id)
VALUES (1, 'Is this car still available?', '2024-06-10T10:30:00', 2, 1),
       (2, 'Great condition, I''m interested!', '2024-06-11T12:45:00', 1, 1),
       (3, 'Can you lower the price a bit?', '2024-06-12T09:00:00', 2, 1),
       (4, 'Does it come with winter tires?', '2024-06-12T15:20:00', 1, 1),
       (5, 'Looks nice! When was the last service?', '2024-06-13T17:30:00', 2, 1);

ALTER TABLE comments
    ALTER COLUMN id RESTART WITH 6;