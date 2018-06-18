/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { JhiDateUtils } from 'ng-jhipster';

import { TipoInmuebleMySuffixService } from '../../../../../../main/webapp/app/entities/tipo-inmueble-my-suffix/tipo-inmueble-my-suffix.service';
import { SERVER_API_URL } from '../../../../../../main/webapp/app/app.constants';

describe('Service Tests', () => {

    describe('TipoInmuebleMySuffix Service', () => {
        let injector: TestBed;
        let service: TipoInmuebleMySuffixService;
        let httpMock: HttpTestingController;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [
                    HttpClientTestingModule
                ],
                providers: [
                    JhiDateUtils,
                    TipoInmuebleMySuffixService
                ]
            });
            injector = getTestBed();
            service = injector.get(TipoInmuebleMySuffixService);
            httpMock = injector.get(HttpTestingController);
        });

        describe('Service methods', () => {
            it('should call correct URL', () => {
                service.find(123).subscribe(() => {});

                const req  = httpMock.expectOne({ method: 'GET' });

                const resourceUrl = SERVER_API_URL + 'api/tipo-inmuebles';
                expect(req.request.url).toEqual(resourceUrl + '/' + 123);
            });
            it('should return TipoInmuebleMySuffix', () => {

                service.find(123).subscribe((received) => {
                    expect(received.body.id).toEqual(123);
                });

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush({id: 123});
            });

            it('should propagate not found response', () => {

                service.find(123).subscribe(null, (_error: any) => {
                    expect(_error.status).toEqual(404);
                });

                const req  = httpMock.expectOne({ method: 'GET' });
                req.flush('Invalid request parameters', {
                    status: 404, statusText: 'Bad Request'
                });

            });
        });

        afterEach(() => {
            httpMock.verify();
        });

    });

});
