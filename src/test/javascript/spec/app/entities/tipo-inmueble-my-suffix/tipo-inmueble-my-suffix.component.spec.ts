/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FrTestModule } from '../../../test.module';
import { TipoInmuebleMySuffixComponent } from '../../../../../../main/webapp/app/entities/tipo-inmueble-my-suffix/tipo-inmueble-my-suffix.component';
import { TipoInmuebleMySuffixService } from '../../../../../../main/webapp/app/entities/tipo-inmueble-my-suffix/tipo-inmueble-my-suffix.service';
import { TipoInmuebleMySuffix } from '../../../../../../main/webapp/app/entities/tipo-inmueble-my-suffix/tipo-inmueble-my-suffix.model';

describe('Component Tests', () => {

    describe('TipoInmuebleMySuffix Management Component', () => {
        let comp: TipoInmuebleMySuffixComponent;
        let fixture: ComponentFixture<TipoInmuebleMySuffixComponent>;
        let service: TipoInmuebleMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FrTestModule],
                declarations: [TipoInmuebleMySuffixComponent],
                providers: [
                    TipoInmuebleMySuffixService
                ]
            })
            .overrideTemplate(TipoInmuebleMySuffixComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TipoInmuebleMySuffixComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TipoInmuebleMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new TipoInmuebleMySuffix(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.tipoInmuebles[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
