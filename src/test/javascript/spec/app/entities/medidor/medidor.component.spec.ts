/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FrTestModule } from '../../../test.module';
import { MedidorComponent } from '../../../../../../main/webapp/app/entities/medidor/medidor.component';
import { MedidorService } from '../../../../../../main/webapp/app/entities/medidor/medidor.service';
import { Medidor } from '../../../../../../main/webapp/app/entities/medidor/medidor.model';

describe('Component Tests', () => {

    describe('Medidor Management Component', () => {
        let comp: MedidorComponent;
        let fixture: ComponentFixture<MedidorComponent>;
        let service: MedidorService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FrTestModule],
                declarations: [MedidorComponent],
                providers: [
                    MedidorService
                ]
            })
            .overrideTemplate(MedidorComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MedidorComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MedidorService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Medidor(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.medidors[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
